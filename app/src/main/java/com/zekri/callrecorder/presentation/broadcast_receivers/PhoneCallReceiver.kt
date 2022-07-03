package com.zekri.callrecorder.presentation.broadcast_receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.telephony.TelephonyManager
import android.widget.Toast
import com.zekri.callrecorder.common.NOTIFICATION_ID
import com.zekri.callrecorder.common.Resources
import com.zekri.callrecorder.domain.use_case.notification_use_cases.NotificationUseCases
import com.zekri.callrecorder.domain.use_case.record_use_cases.RecordUseCases
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
abstract class PhoneCallReceiver : BroadcastReceiver() {
    var recorder: MediaRecorder? = null

    @Inject
    lateinit var recordUseCases: RecordUseCases

    @Inject
    lateinit var notificationUseCases: NotificationUseCases

    //The receiver will be recreated whenever android feels like it.  We need a static variable to remember data between instantiations
    private var lastState = TelephonyManager.CALL_STATE_IDLE
    private var callStartTime: Date? = null
    private var isIncoming = false
    private var recordInProgress = false
    private var savedNumber //because the passed incoming is only valid in ringing
            : String? = null

    override fun onReceive(context: Context, intent: Intent) {
//        startRecording();
        //We listen to two intents.  The new outgoing call only tells us of an outgoing call.  We use it to get the number.

        intent.extras?.apply {
            if (intent.action == "android.intent.action.NEW_OUTGOING_CALL") {
                savedNumber = getString("android.intent.extra.PHONE_NUMBER")
            } else {


                val stateStr = getString(TelephonyManager.EXTRA_STATE)
                val number = getString(TelephonyManager.EXTRA_INCOMING_NUMBER)
                var state = 0
                when (stateStr) {
                    TelephonyManager.EXTRA_STATE_IDLE -> {
                        state = TelephonyManager.CALL_STATE_IDLE
                    }
                    TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                        state = TelephonyManager.CALL_STATE_OFFHOOK
                    }
                    TelephonyManager.EXTRA_STATE_RINGING -> {
                        state = TelephonyManager.CALL_STATE_RINGING
                    }
                }
                onCallStateChanged(context, state, number)
            }
        }
    }


    //Derived classes should override these to respond to specific events of interest
    protected abstract fun onIncomingCallReceived(ctx: Context?, number: String?, start: Date?)
    protected abstract fun onIncomingCallAnswered(ctx: Context?, number: String?, start: Date?)
    protected abstract fun onIncomingCallEnded(
        ctx: Context?,
        number: String?,
        start: Date?,
        end: Date?
    )

    protected abstract fun onOutgoingCallStarted(ctx: Context?, number: String?, start: Date?)
    protected abstract fun onOutgoingCallEnded(
        ctx: Context?,
        number: String?,
        start: Date?,
        end: Date?
    )

    protected abstract fun onMissedCall(ctx: Context?, number: String?, start: Date?)

    //Deals with actual events
    //Incoming call-  goes from IDLE to RINGING when it rings, to OFFHOOK when it's answered, to IDLE when its hung up
    //Outgoing call-  goes from IDLE to OFFHOOK when it dials out, to IDLE when hung up
    private fun onCallStateChanged(context: Context?, state: Int, number: String?) {
        if (lastState == state) {
            //No change, debounce extras
            return
        }
        when (state) {
            TelephonyManager.CALL_STATE_RINGING -> {
                isIncoming = true
                callStartTime = Date()
                savedNumber = number
                onIncomingCallReceived(context, number, callStartTime)
            }
            TelephonyManager.CALL_STATE_OFFHOOK ->                     //Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                    isIncoming = false
                    callStartTime = Date()

                    onOutgoingCallStarted(context, savedNumber, callStartTime)
                } else {
                    isIncoming = true
                    callStartTime = Date()

                    onIncomingCallAnswered(context, savedNumber, callStartTime)
                }
            TelephonyManager.CALL_STATE_IDLE ->                     //Went to idle-  this is the end of a call.  What type depends on previous state(s)
                if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                    //Ring but no pickup-  a miss
                    onMissedCall(context, savedNumber, callStartTime)
                } else if (isIncoming)

                    onIncomingCallEnded(context, savedNumber, callStartTime, Date())
                else

                    onOutgoingCallEnded(context, savedNumber, callStartTime, Date())

        }
        lastState = state
    }

    fun recordCall(ctx: Context?, number: String?) {
        val scope = CoroutineScope(Dispatchers.IO)
        recordInProgress = true
        ctx?.apply {


            recordUseCases.createFileUseCase("records", number ?: "number").onEach {
                when (it) {

                    is Resources.Success ->
                        it.data?.run {
                            recordUseCases.startRecordUseCase(this@apply, this)
                                .onEach { result ->
                                    when (result) {
                                        is Resources.Success ->


                                            recorder = result.data


                                        is Resources.Error -> {
                                        }
                                        is Resources.Loading -> {

                                        }
                                    }

                                }.launchIn(scope)
                        }
                    is Resources.Error ->
                        Toast.makeText(
                            this@apply,
                            it.error,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    is Resources.Loading -> {

                    }
                }

            }
                .launchIn(scope)

        }
    }


    fun stopRecord() {
        recordInProgress = false
        recorder?.apply {
            recordUseCases.stopRecordUseCase(this)
            recorder = null
        }
    }

    fun showNotification(context: Context?) {
        if (context == null)
            return
        val scope = CoroutineScope(Dispatchers.IO)


        notificationUseCases.createNotificationChannel(context).onEach {

            when (it) {
                is Resources.Success -> notificationUseCases.buildNotification(context)
                    .onEach { result ->
                        if (result is Resources.Success) {
                            var timer = 0
                            while (recordInProgress) {


                                timer++
                                result.data?.apply {
                                    notificationUseCases.modifyNotification(
                                        this,
                                        "Recording",
                                        timer.toString()
                                    ).onEach {

                                            modificationResults ->
                                        if (modificationResults is Resources.Success)
                                            notificationUseCases.showNotification(
                                                this,
                                                context,
                                                notificationId = NOTIFICATION_ID
                                            )
                                                .launchIn(scope)

                                    }.launchIn(scope)
                                }
                                delay(1000)


                            }
                            notificationUseCases.cancelNotification(NOTIFICATION_ID, context)
                                .launchIn(scope)


                        }
                    }
                    .launchIn(scope)
                is Resources.Error -> {}


                else -> {}
            }


        }.launchIn(scope)


    }
}
