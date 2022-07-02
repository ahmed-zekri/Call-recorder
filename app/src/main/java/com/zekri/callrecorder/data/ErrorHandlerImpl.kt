package com.zekri.callrecorder.data

import com.zekri.callrecorder.common.ErrorEntity
import com.zekri.callrecorder.domain.ErrorHandler
import java.io.IOException
import java.lang.Exception

class ErrorHandlerImpl : ErrorHandler {
    override fun getError(exception: Exception): ErrorEntity = when (exception) {

        is SecurityException -> ErrorEntity.AccessDenied(exception.message ?: "")
        is IOException -> ErrorEntity.ResourcesProtected(exception.message ?: "")

        else -> ErrorEntity.Unknown(exception.message ?: "")


    }
}