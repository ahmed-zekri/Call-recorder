package com.zekri.callrecorder.data

import com.zekri.callrecorder.domain.AddDeviceAdmin
import com.zekri.callrecorder.domain.AddDeviceAdminImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Provides
    @Singleton
    fun providesCheckDeviceAdmin(): AddDeviceAdmin = AddDeviceAdminImpl()
}