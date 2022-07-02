package com.zekri.callrecorder.presentation.services

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import com.zekri.callrecorder.common.ACTION_IN
import com.zekri.callrecorder.common.ACTION_OUT
import com.zekri.callrecorder.presentation.broadcast_receivers.PhoneCallReceiverImpl


class CallService : Service() {


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("StartService", packageName.javaClass.name)
        val filter = IntentFilter().apply {
            addAction(ACTION_OUT)
            addAction(ACTION_IN)
        }
        this.registerReceiver(PhoneCallReceiverImpl(), filter)
        return super.onStartCommand(intent, flags, startId)

    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


}