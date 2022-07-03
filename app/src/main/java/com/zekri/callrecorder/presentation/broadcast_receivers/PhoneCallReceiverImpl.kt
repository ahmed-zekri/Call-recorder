package com.zekri.callrecorder.presentation.broadcast_receivers

import android.content.Context
import android.media.MediaRecorder
import android.util.Log
import android.widget.Toast
import com.zekri.callrecorder.common.Resources
import com.zekri.callrecorder.domain.use_case.notification_use_cases.NotificationUseCases
import com.zekri.callrecorder.domain.use_case.record_use_cases.RecordUseCases
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject



class PhoneCallReceiverImpl : PhoneCallReceiver() {


    override fun onIncomingCallReceived(ctx: Context?, number: String?, start: Date?) {
        recordCall(ctx, number)


    }


    override fun onIncomingCallAnswered(ctx: Context?, number: String?, start: Date?) {
        Log.d("onIncomingCallAnswered", "$number $start")
        recordCall(ctx, number)
        showNotification(ctx)
    }

    override fun onIncomingCallEnded(
        ctx: Context?,
        number: String?,
        start: Date?,
        end: Date?
    ) {
        Log.d("onIncomingCallEnded", "$number $start\t$end")
        stopRecord()
    }


    override fun onOutgoingCallStarted(ctx: Context?, number: String?, start: Date?) {
        Log.d("onOutgoingCallStarted", "$number $start")
        recordCall(ctx, number)
        showNotification(ctx)
    }

    override fun onOutgoingCallEnded(
        ctx: Context?,
        number: String?,
        start: Date?,
        end: Date?
    ) {
        stopRecord()

    }

    override fun onMissedCall(ctx: Context?, number: String?, start: Date?) {
        Log.d("onMissedCall", "$number $start")

    }


}