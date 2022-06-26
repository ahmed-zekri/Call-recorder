package com.zekri.callrecorder

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


class DeviceAdminDemo : DeviceAdminReceiver() {
    private fun showToast(context: Context, msg: String) {

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    }

    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        showToast(context, context.getString(R.string.admin_receiver_status_enabled))
    }

    override fun onDisableRequested(context: Context, intent: Intent): CharSequence =
        context.getString(R.string.admin_receiver_status_disable_warning)

    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
        showToast(context, context.getString(R.string.admin_receiver_status_disabled))
    }

}
