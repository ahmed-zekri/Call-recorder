package com.zekri.callrecorder.domain.use_case.record_use_cases

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import com.zekri.callrecorder.common.Resources
import com.zekri.callrecorder.domain.ErrorHandler
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject


class StartRecordUseCase @Inject constructor(private val errorHandler: ErrorHandler) {


    operator fun invoke(context: Context, file: File) = flow {
        emit(Resources.Loading())

        val recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            MediaRecorder(context) else
            MediaRecorder()



        with(recorder) {

            setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
            setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(file.absolutePath)



        }
        try {
            recorder.prepare()
            recorder.start()
        } catch (e: Exception) {
            errorHandler.getError(exception = e)
            emit(Resources.Error(e.message ?: ""))
            return@flow
        }


        emit(Resources.Success(recorder))


    }


}