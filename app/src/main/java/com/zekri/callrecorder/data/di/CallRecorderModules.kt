package com.zekri.callrecorder.data.di

import com.zekri.callrecorder.domain.ErrorHandler
import com.zekri.callrecorder.domain.use_case.CreateFileUseCase
import com.zekri.callrecorder.domain.use_case.RecordUseCases
import com.zekri.callrecorder.domain.use_case.StartRecordUseCase
import com.zekri.callrecorder.domain.use_case.StopRecordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CallRecorderModules {
    @Singleton
    @Provides
    fun providesStartRecord(errorHandler: ErrorHandler): StartRecordUseCase =
        StartRecordUseCase(errorHandler)

    @Singleton
    @Provides
    fun providesStopRecord(

        errorHandler: ErrorHandler
    ): StopRecordUseCase =
        StopRecordUseCase(errorHandler = errorHandler)

    @Singleton
    @Provides
    fun providesCreateFileUseCase(

        errorHandler: ErrorHandler
    ): CreateFileUseCase =
        CreateFileUseCase(errorHandler = errorHandler)


    @Singleton
    @Provides
    fun providesRecordUseCases(

        startRecordUseCase: StartRecordUseCase,
        stopRecordUseCase: StopRecordUseCase,
        createFileUseCase: CreateFileUseCase
    ): RecordUseCases =
        RecordUseCases(
            startRecordUseCase = startRecordUseCase,
            stopRecordUseCase = stopRecordUseCase,
            createFileUseCase = createFileUseCase
        )


}