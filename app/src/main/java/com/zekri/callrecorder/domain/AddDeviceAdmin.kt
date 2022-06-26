package com.zekri.callrecorder.domain

import android.content.Context
import com.zekri.callrecorder.common.Resources
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


interface AddDeviceAdmin {

    operator fun invoke(context: Context): Flow<Resources<Boolean>>
}


class AddDeviceAdminImpl : AddDeviceAdmin {
    override fun invoke(context: Context): Flow<Resources<Boolean>> =
        callbackFlow {


            awaitClose()
        }
}

