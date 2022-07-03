package com.zekri.callrecorder.domain.use_case

import android.os.Environment
import com.zekri.callrecorder.common.Resources
import com.zekri.callrecorder.domain.ErrorHandler
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject


class CreateFileUseCase @Inject constructor(private val errorHandler: ErrorHandler) {


    operator fun invoke(dirName: String, fileName: String) = flow {
        val dir = File(Environment.getExternalStoragePublicDirectory(""), "/$dirName")
        var audioFile: File?
        dir.apply {

            if (!exists())
                mkdirs()

            fileName.apply {
                try {
                    audioFile = File.createTempFile(this, ".amr", dir)
                } catch (e: Exception) {
                    errorHandler.getError(e)
                    emit(Resources.Error(e.message ?: ""))
                    return@flow
                }


                emit(Resources.Success(audioFile))
            }


        }

    }


}

