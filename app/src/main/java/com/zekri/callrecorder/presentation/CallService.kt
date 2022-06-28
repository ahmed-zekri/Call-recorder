package com.zekri.callrecorder.presentation

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.os.IBinder
import android.widget.Toast
import com.zekri.callrecorder.common.ACTION_IN
import com.zekri.callrecorder.common.ACTION_OUT
import java.util.*


class CallService : Service() {

    private lateinit var br_call: CallBackground
    var name: String? = null
    var phonenumber: kotlin.String? = null
    var audio_format: String? = null
    var Audio_Type: String? = null
    var audioSource = 0
    var context: Context? = null
    private val handler: Handler? = null
    var timer: Timer? = null

    var ringing: kotlin.Boolean? = false
    var toast: Toast? = null
    var isOffHook = false


    private val callBackground: CallService? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val filter = IntentFilter()
        filter.addAction(ACTION_OUT)
        filter.addAction(ACTION_IN)
        this.br_call = CallBackground()
        this.registerReceiver(this.br_call, filter)

        // if(terminate != null) {
        // stopSelf();
        // }

        // if(terminate != null) {
        // stopSelf();
        // }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}