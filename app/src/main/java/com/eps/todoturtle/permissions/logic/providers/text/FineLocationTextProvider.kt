package com.eps.todoturtle.permissions.logic.providers.text

import android.content.Context

class FineLocationTextProvider(context: Context) : PermissionTextProvider {

    override val isPermanentlyDeclined: String
        get() = """It seems you permanently declined coarse location permission.
            |  You can got app settings to grant it.
        """.trimMargin()
    override val normalDecline: String
        get() = "This app needs access to your coarse location so it can track you better"
}
