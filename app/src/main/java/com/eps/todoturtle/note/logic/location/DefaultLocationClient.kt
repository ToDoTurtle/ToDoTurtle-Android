package com.eps.todoturtle.note.logic.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Task

class DefaultLocationClient(
    private val context: Context,
    private val client: FusedLocationProviderClient,
) : LocationClient {
    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(): Task<Location> {
        if (!context.hasLocationPermission()) {
            throw LocationClient.LocationException("Missing location permission")
        }
        if (!context.isGpsEnabled() && !context.isNetworkLocationEnabled()) {
            throw LocationClient.LocationException("GPS is disabled")
        }

        return client.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            null,
        )
    }
}
