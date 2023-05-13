package com.eps.todoturtle.note.logic.location

import android.location.Location
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getCurrentLocation(): Task<Location>

    class LocationException(message: String) : Exception(message)
}