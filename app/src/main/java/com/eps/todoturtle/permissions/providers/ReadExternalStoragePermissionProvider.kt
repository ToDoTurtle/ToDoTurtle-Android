package com.eps.todoturtle.permissions.providers

import android.Manifest
import com.eps.todoturtle.permissions.providers.text.ReadExternalStorageTextProvider

class ReadExternalStoragePermissionProvider : PermissionProvider {
    override val textProvider = ReadExternalStorageTextProvider()
    override val permission = Manifest.permission.READ_EXTERNAL_STORAGE
}
