package com.zekri.callrecorder.domain.use_case.notification_use_cases

data class NotificationUseCases(
    val createNotificationChannel: CreateNotificationChannel,
    val modifyNotification: ModifyNotification,
    val showNotification: ShowNotification,
    val buildNotification: BuildNotification,
    val cancelNotification: CancelNotification

)