package com.eps.todoturtle.permissions.logic.providers

import com.eps.todoturtle.permissions.logic.providers.text.PermissionTextProvider

interface PermissionProvider {
    val textProvider: PermissionTextProvider
    val permission: String
}
