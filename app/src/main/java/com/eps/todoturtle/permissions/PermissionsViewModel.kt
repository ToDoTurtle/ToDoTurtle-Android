package com.eps.todoturtle.permissions

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class PermissionsViewModel: ViewModel() {
    val declinedPermissionQueue = mutableStateListOf<String>()

    fun onDismissDialog() {
        declinedPermissionQueue.removeFirst()
    }

    fun onPermissionResult(permission: String, isGranted: Boolean) {
        if (!isGranted && !declinedPermissionQueue.contains(permission)) {
            declinedPermissionQueue.add(permission)
        }
    }
}