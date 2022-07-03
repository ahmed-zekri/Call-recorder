package com.zekri.callrecorder.domain.use_case.notification_use_cases

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.zekri.callrecorder.common.Resources
import com.zekri.callrecorder.domain.ErrorHandler
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class CancelNotification @Inject constructor(private val errorHandler: ErrorHandler) {


    operator fun invoke(notificationId: Int, context: Context) = flow {
        context.apply {
            try {
                with(NotificationManagerCompat.from(this)) {
                    // notificationId is a unique int for each notification that you must define
                    cancel(notificationId)
                    emit(Resources.Success(notificationId))
                }

            } catch (e: Exception) {
                errorHandler.getError(exception = e)
                emit(Resources.Error<Resources<Int>>(e.message ?: ""))
            }


        }

    }


}




