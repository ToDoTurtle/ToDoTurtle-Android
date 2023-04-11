package com.eps.todoturtle.permissions.providers.text

interface PermissionTextProvider {
    val isPermanentlyDeclined: String
    val normalDecline: String

    fun getDescription(isPermanentlyDeclined: Boolean) =
        if (isPermanentlyDeclined) this.isPermanentlyDeclined else normalDecline
}