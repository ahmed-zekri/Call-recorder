package com.zekri.callrecorder.domain.use_case.notification_use_cases

import android.content.Context
import androidx.core.app.NotificationCompat
import com.zekri.callrecorder.R
import com.zekri.callrecorder.common.CHANNEL_ID
import com.zekri.callrecorder.common.Resources
import com.zekri.callrecorder.domain.ErrorHandler
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class ModifyNotification @Inject constructor(private val errorHandler: ErrorHandler) {


    operator fun invoke(
        builder: NotificationCompat.Builder,
        title: String,
        content: String,
        bigText: String = ""
    ) = flow {

        try {
            builder.setContentTitle(title)
                .setContentText(content)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(bigText)
                )
            emit(Resources.Success(builder))
        } catch (e: Exception) {
            errorHandler.getError(exception = e)
            emit(Resources.Error(e.message ?: ""))
        }


    }


}




