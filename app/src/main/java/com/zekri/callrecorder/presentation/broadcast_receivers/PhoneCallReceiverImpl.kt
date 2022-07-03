package com.zekri.callrecorder.presentation.broadcast_receivers

import android.content.Context
import android.util.Log
import java.util.*


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