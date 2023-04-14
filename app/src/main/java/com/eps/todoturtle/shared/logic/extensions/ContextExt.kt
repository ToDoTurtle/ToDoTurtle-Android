package com.eps.todoturtle.shared.logic.extensions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.datastore.dataStore
import com.eps.todoturtle.preferences.logic.AppPreferencesSerializer

private const val DATA_STORE_FN = "user-preferences.json"

fun Context.hasCameraPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.CAMERA,
    ) == PackageManager.PERMISSION_GRANTED
}

val Context.dataStore by dataStore(
    fileName = DATA_STORE_FN,
    AppPreferencesSerializer,
)
