package com.zekri.callrecorder.common

sealed class Resources<T>(
    val loading: Boolean = false,
    val error: String = "",
    val data: T? = null
) {
    class Loading<T> : Resources<T>(loading = true)
    class Success<T>(data: T) : Resources<T>(data = data)
    class Error<T>(error: String) : Resources<T>(error = error)

}