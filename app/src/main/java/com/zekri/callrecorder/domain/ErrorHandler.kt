package com.zekri.callrecorder.domain

import com.zekri.callrecorder.common.ErrorEntity
import java.lang.Exception

interface ErrorHandler {
    fun getError(exception: Exception): ErrorEntity

}