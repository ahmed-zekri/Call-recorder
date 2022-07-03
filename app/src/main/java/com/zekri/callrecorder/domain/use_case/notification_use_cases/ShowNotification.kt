package com.zekri.callrecorder.domain.use_case.notification_use_cases

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.zekri.callrecorder.common.NOTIFICATION_ID
import com.zekri.callrecorder.common.Resources
import com.zekri.callrecorder.domain.ErrorHandler
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class ShowNotification @Inject constructor(private val errorHandler: ErrorHandler) {


    operator fun invoke(
        builder: NotificationCompat.Builder,
        context: Context,
        notificationId: Int
    ) = flow {
        context.apply {
            try {
                with(NotificationManagerCompat.from(this)) {
                    // notificationId is a unique int for each notification that you must define
                    notify(notificationId, builder.build())
                }
                emit(Resources.Success(notificationId))
            } catch (e: Exception) {
                errorHandler.getError(exception = e)
                emit(Resources.Error<String>(e.message ?: ""))
            }


        }

    }


}




