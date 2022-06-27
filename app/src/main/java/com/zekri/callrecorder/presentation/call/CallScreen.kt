package com.zekri.callrecorder.presentation.call

import android.app.Activity.RESULT_OK
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zekri.callrecorder.DeviceAdminDemo

@Composable
fun CallScreen(recordCallViewModel: RecordCallViewModel = viewModel()) {
    val context = LocalContext.current
    val snackBarState = remember { mutableStateOf(false) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK)
                launchService()
            else
                snackBarState.value = true

        }

    LaunchedEffect(key1 = true) {

        val componentName = ComponentName(context, DeviceAdminDemo::class.java)
        val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Example")
        launcher.launch(intent)
    }
    Box(modifier = Modifier.fillMaxSize()) {


        Snackbar(


            modifier = Modifier.padding(8.dp).align(Alignment.BottomCenter)
        ) { Text(text = "Please add the app as administrator") }


    }
}

fun launchService() {

}
