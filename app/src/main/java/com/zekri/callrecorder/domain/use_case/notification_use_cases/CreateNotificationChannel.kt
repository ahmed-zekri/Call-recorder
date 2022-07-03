package com.zekri.callrecorder.domain.use_case.notification_use_cases

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import com.zekri.callrecorder.R
import com.zekri.callrecorder.common.CHANNEL_ID
import com.zekri.callrecorder.common.Resources
import com.zekri.callrecorder.domain.ErrorHandler
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class CreateNotificationChannel @Inject constructor(private val errorHandler: ErrorHandler) {


    operator fun invoke(context: Context) = flow {
        context.apply {
            try {

                // Create the NotificationChannel
                val name = getString(R.string.channel_name)
                val descriptionText = getString(R.string.channel_description)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
                mChannel.description = descriptionText
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                val notificationManager =
                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(mChannel)
                emit(Resources.Success(CHANNEL_ID))
            } catch (e: Exception) {
                errorHandler.getError(exception = e)
                emit(Resources.Error<String>(e.message ?: ""))
            }


        }

    }


}




