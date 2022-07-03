package com.zekri.callrecorder.domain.use_case.notification_use_cases

import android.content.Context
import androidx.core.app.NotificationCompat
import com.zekri.callrecorder.R
import com.zekri.callrecorder.common.CHANNEL_ID
import com.zekri.callrecorder.common.Resources
import com.zekri.callrecorder.domain.ErrorHandler
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class BuildNotification @Inject constructor(private val errorHandler: ErrorHandler) {


    operator fun invoke(context: Context) = flow {
        context.apply {
            try {
                val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)

                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                emit(Resources.Success(builder))

            } catch (e: Exception) {
                errorHandler.getError(exception = e)
                emit(Resources.Error<NotificationCompat.Builder>(e.message ?: ""))
            }


        }

    }


}




