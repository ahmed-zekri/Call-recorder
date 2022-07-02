package com.zekri.callrecorder.data.di

import com.zekri.callrecorder.data.ErrorHandlerImpl
import com.zekri.callrecorder.domain.ErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun providesErrorHandler(): ErrorHandler = ErrorHandlerImpl()

}