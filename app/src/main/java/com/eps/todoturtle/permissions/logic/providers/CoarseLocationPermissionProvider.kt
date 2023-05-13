package com.eps.todoturtle.permissions.logic.providers

import android.Manifest
import android.content.Context
import com.eps.todoturtle.permissions.logic.providers.text.CoarseLocationTextProvider

class CoarseLocationPermissionProvider(context: Context): PermissionProvider {
    override val textProvider = CoarseLocationTextProvider(context)
    override val permission = Manifest.permission.ACCESS_COARSE_LOCATION
}