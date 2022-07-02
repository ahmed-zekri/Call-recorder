package com.zekri.callrecorder.domain.use_case

import android.media.MediaRecorder
import com.zekri.callrecorder.common.Resources
import com.zekri.callrecorder.domain.ErrorHandler
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class StopRecordUseCase @Inject constructor(private val errorHandler: ErrorHandler) {


    operator fun invoke(mediaRecorder: MediaRecorder) = flow {
        emit(Resources.Loading())


        try {
            mediaRecorder.stop()
        } catch (e: Exception) {
            errorHandler.getError(exception = e)
            emit(Resources.Error(e.message ?: ""))
            return@flow
        }


        emit(Resources.Success(true))


    }


}