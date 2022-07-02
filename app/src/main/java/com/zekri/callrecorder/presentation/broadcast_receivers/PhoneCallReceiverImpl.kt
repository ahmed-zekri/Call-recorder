package com.zekri.callrecorder.presentation.broadcast_receivers

import android.content.Context
import android.media.MediaRecorder
import android.util.Log
import android.widget.Toast
import com.zekri.callrecorder.common.Resources
import com.zekri.callrecorder.domain.use_case.RecordUseCases
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class PhoneCallReceiverImpl : PhoneCallReceiver() {
    private var recorder: MediaRecorder? = null

    @Inject
    lateinit var recordUseCases: RecordUseCases
    override fun onIncomingCallReceived(ctx: Context?, number: String?, start: Date?) {
        recordCall(ctx, number)


    }

    private fun recordCall(ctx: Context?, number: String?) {
        val scope = CoroutineScope(Dispatchers.IO)
        ctx?.apply {
            recordUseCases.createFileUseCase("records", number ?: "number").onEach {
                when (it) {

                    is Resources.Success ->
                        it.data?.run {
                            recordUseCases.startRecordUseCase(this@apply, this).onEach { result ->
                                when (result) {
                                    is Resources.Success -> {
                                        Toast.makeText(
                                            ctx,
                                            "Record started",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        recorder = result.data


                                    }
                                    is Resources.Error -> Toast.makeText(
                                        this@apply,
                                        it.error,
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    is Resources.Loading -> TODO()
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


    override fun onIncomingCallAnswered(ctx: Context?, number: String?, start: Date?) {
        Log.d("onIncomingCallAnswered", "$number $start")
    }

    override fun onIncomingCallEnded(
        ctx: Context?,
        number: String?,
        start: Date?,
        end: Date?
    ) {
        Log.d("onIncomingCallEnded", "$number $start\t$end")
    }

    override fun onOutgoingCallStarted(ctx: Context?, number: String?, start: Date?) {
        Log.d("onOutgoingCallStarted", "$number $start")
        recordCall(ctx, number)
    }

    override fun onOutgoingCallEnded(
        ctx: Context?,
        number: String?,
        start: Date?,
        end: Date?
    ) {
        Log.d("onOutgoingCallEnded", "$number $start\t$end")
    }

    override fun onMissedCall(ctx: Context?, number: String?, start: Date?) {
        Log.d("onMissedCall", "$number $start")

    }
}