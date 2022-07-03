package com.zekri.callrecorder.data.di

import com.zekri.callrecorder.domain.ErrorHandler
import com.zekri.callrecorder.domain.use_case.notification_use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NotificationModules {
    @Singleton
    @Provides
    fun providesCreateNotificationChannel(errorHandler: ErrorHandler): CreateNotificationChannel =
        CreateNotificationChannel(errorHandler)

    @Singleton
    @Provides
    fun providesModifyNotifications(

        errorHandler: ErrorHandler
    ): ModifyNotification =
        ModifyNotification(errorHandler = errorHandler)

    @Singleton
    @Provides
    fun providesShowNotifications(

        errorHandler: ErrorHandler
    ): ShowNotification =
        ShowNotification(errorHandler = errorHandler)

    @Singleton
    @Provides
    fun providesCancelNotification(

        errorHandler: ErrorHandler
    ): CancelNotification =
        CancelNotification(errorHandler = errorHandler)


    @Singleton
    @Provides
    fun providesNotificationsUseCases(

        showNotification: ShowNotification,
        createNotificationChannel: CreateNotificationChannel,
        modifyNotification: ModifyNotification,
        buildNotification: BuildNotification,
        cancelNotification: CancelNotification
    ): NotificationUseCases =
        NotificationUseCases(
            createNotificationChannel = createNotificationChannel,
            modifyNotification = modifyNotification,
            showNotification = showNotification,
            buildNotification = buildNotification,
            cancelNotification = cancelNotification
        )


}