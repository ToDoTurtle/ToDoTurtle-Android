package com.eps.todoturtle.permissions.logic.providers.text

import android.content.Context
import com.eps.todoturtle.R

class CameraTextProvider(private val context: Context) : PermissionTextProvider {
    override val isPermanentlyDeclined: String
        get() = context.getString(R.string.camera_permanently_declined)
    override val normalDecline: String
        get() = context.getString(R.string.camera_normal_decline)
}
