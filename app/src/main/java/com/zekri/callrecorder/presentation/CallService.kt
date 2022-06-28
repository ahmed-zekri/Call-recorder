package com.zekri.callrecorder.presentation

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.os.Handler
import android.os.IBinder
import android.widget.Toast
import java.io.File
import java.util.*


class CallService : Service() {

    var name: String? = null
    var phonenumber:kotlin.String? = null
    var audio_format: String? = null
    var Audio_Type: String? = null
    var audioSource = 0
    var context: Context? = null
    private val handler: Handler? = null
    var timer: Timer? = null

    var ringing:kotlin.Boolean? = false
    var toast: Toast? = null
    var isOffHook = false




    private val callBackground: CallService? = null



    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}