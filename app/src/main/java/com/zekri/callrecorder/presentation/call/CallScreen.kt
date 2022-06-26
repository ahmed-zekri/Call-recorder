package com.zekri.callrecorder.presentation.call

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CallScreen(recordCallViewModel: RecordCallViewModel = viewModel()) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {

        recordCallViewModel.addDeviceAdmin(context)

    }

}