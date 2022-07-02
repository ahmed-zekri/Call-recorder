package com.zekri.callrecorder.common

sealed class ErrorEntity(val message: String = "") {


    class AccessDenied(exceptionMessage: String) :
        ErrorEntity(message = exceptionMessage)


    class Unknown(exceptionMessage: String) : ErrorEntity(message = exceptionMessage)
    class ResourcesProtected(exceptionMessage: String) : ErrorEntity(message = exceptionMessage)
}
