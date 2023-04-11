package com.eps.todoturtle.permissions.providers

import com.eps.todoturtle.permissions.providers.text.PermissionTextProvider

interface PermissionProvider {
    val textProvider: PermissionTextProvider
    val permission: String
}