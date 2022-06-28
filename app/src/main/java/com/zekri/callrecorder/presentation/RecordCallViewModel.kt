package com.zekri.callrecorder.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zekri.callrecorder.domain.AddDeviceAdmin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RecordCallViewModel @Inject constructor(private val addDeviceAdmin: AddDeviceAdmin) :
    ViewModel() {


}