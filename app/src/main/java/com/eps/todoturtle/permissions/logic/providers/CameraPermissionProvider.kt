package com.eps.todoturtle.permissions.logic.providers

import android.Manifest
import com.eps.todoturtle.permissions.logic.providers.text.CameraTextProvider

class CameraPermissionProvider : PermissionProvider {
    override val textProvider = CameraTextProvider()
    override val permission = Manifest.permission.CAMERA
}
