package com.zekri.callrecorder.domain.use_case

data class RecordUseCases(
    val startRecordUseCase: StartRecordUseCase,
    val stopRecordUseCase: StopRecordUseCase,
    val createFileUseCase: CreateFileUseCase
)