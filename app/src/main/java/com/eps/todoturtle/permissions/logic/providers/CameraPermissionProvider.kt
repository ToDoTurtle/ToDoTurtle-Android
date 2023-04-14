package com.eps.todoturtle.permissions.logic.providers

import android.Manifest
import android.content.Context
import com.eps.todoturtle.permissions.logic.providers.text.CameraTextProvider

class CameraPermissionProvider(context: Context) : PermissionProvider {
    override val textProvider = CameraTextProvider(context)
    override val permission = Manifest.permission.CAMERA
}
