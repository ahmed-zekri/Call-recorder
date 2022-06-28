package com.zekri.callrecorder.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.telephony.TelephonyManager
import android.widget.Toast
import com.zekri.callrecorder.common.ACTION_IN
import com.zekri.callrecorder.common.ACTION_OUT
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class CallBackground : BroadcastReceiver() {
    var bundle: Bundle? = null
    var state: String? = null
    var inCall: String? = null
    var outCall: String? = null
    var wasRinging = false
    private var recordStarted = false
    private lateinit var recorder: MediaRecorder
    lateinit var audiofile: File

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        if (intent.action.equals(ACTION_IN)) {
            if (intent.extras.also { bundle = it } != null) {
                state = bundle!!.getString(TelephonyManager.EXTRA_STATE)
                if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                    inCall = bundle!!.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)
                    wasRinging = true
                    Toast.makeText(context, "IN : $inCall", Toast.LENGTH_LONG).show()
                } else if (state == TelephonyManager.EXTRA_STATE_OFFHOOK) {
                    if (wasRinging) {
                        Toast.makeText(context, "ANSWERED", Toast.LENGTH_LONG).show()
                        SimpleDateFormat("dd-MM-yyyy hh-mm-ss").format(Date())
                        val sampleDir =
                            File(Environment.getExternalStorageDirectory(), "/TestRecordingDasa1")
                        if (!sampleDir.exists()) {
                            sampleDir.mkdirs()
                        }
                        val file_name = "Record"
                        try {
                            audiofile = File.createTempFile(file_name, ".amr", sampleDir)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        Environment.getExternalStorageDirectory().absolutePath
                        recorder = MediaRecorder()
                        //                          recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
                        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
                        recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
                        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                        recorder.setOutputFile(audiofile.absolutePath)
                        try {
                            recorder.prepare()
                        } catch (e: IllegalStateException) {
                            e.printStackTrace()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        recorder.start()
                        recordStarted = true
                    }
                } else if (state == TelephonyManager.EXTRA_STATE_IDLE) {
                    wasRinging = false
                    Toast.makeText(context, "REJECT || DISCO", Toast.LENGTH_LONG).show()
                    if (recordStarted) {
                        recorder.stop()
                        recordStarted = false
                    }
                }
            }
        } else if (intent.action.equals(ACTION_OUT)) {
            if (intent.extras.also { bundle = it } != null) {
                outCall = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
                Toast.makeText(context, "OUT : $outCall", Toast.LENGTH_LONG).show()
            }
        }
    }
}