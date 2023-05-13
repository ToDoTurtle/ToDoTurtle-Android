package com.eps.todoturtle.permissions.logic.providers

import android.Manifest
import android.content.Context
import com.eps.todoturtle.permissions.logic.providers.text.FineLocationTextProvider

class FineLocationPermissionProvider(context: Context): PermissionProvider {
    override val textProvider = FineLocationTextProvider(context)
    override val permission = Manifest.permission.ACCESS_FINE_LOCATION
}