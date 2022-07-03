package com.zekri.callrecorder.domain.use_case.record_use_cases

data class RecordUseCases(
    val startRecordUseCase: StartRecordUseCase,
    val stopRecordUseCase: StopRecordUseCase,
    val createFileUseCase: CreateFileUseCase
)