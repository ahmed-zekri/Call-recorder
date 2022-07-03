package com.zekri.callrecorder.presentation

import android.app.Activity.RESULT_OK
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
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
import androidx.test.core.app.ApplicationProvider
import com.zekri.callrecorder.DeviceAdminAdd
import com.zekri.callrecorder.common.checkStoragePermission
import com.zekri.callrecorder.presentation.services.CallService

fun launchService(context: Context) {
    val intent = Intent(context, CallService::class.java)
    context.startService(intent)
}

@Composable
fun CallScreen(recordCallViewModel: RecordCallViewModel = viewModel()) {
    val context = LocalContext.current
    val snackBarState = remember { mutableStateOf(false) }
    val launcherStorage =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK)
                launchService(context = context)
            else
                snackBarState.value = true

        }

    fun checkStorage(context: Context) {

        context.run {
            if (!checkStoragePermission()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)

                    Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                        addCategory("android.intent.category.DEFAULT")
                        data =
                            Uri.parse(
                                String.format(
                                    "package:%s",
                              applicationContext.packageName
                                )
                            )
                        launcherStorage.launch(this)

                    }

            } else launchService(context)

        }
    }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK)
                checkStorage(context)
            else
                snackBarState.value = true

        }







    LaunchedEffect(key1 = true) {
        val mDPM = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager?

        val componentName = ComponentName(context, DeviceAdminAdd::class.java)
        if (!mDPM!!.isAdminActive(componentName)) {
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            intent.apply {
                putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
                intent.putExtra(
                    DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    "Please add the app as admin"
                )
                launcher.launch(this)
            }


        } else
            checkStorage(context)
    }
    Box(modifier = Modifier.fillMaxSize()) {


        Snackbar(


            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.BottomCenter)
        ) { Text(text = "Please add the app as administrator") }


    }

}