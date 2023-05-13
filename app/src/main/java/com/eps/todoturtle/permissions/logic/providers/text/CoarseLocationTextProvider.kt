package com.eps.todoturtle.permissions.logic.providers.text

import android.content.Context

class CoarseLocationTextProvider(context: Context) : PermissionTextProvider {

        override val isPermanentlyDeclined: String
            get() = """It seems you permanently declined fine location permission.
                |You can got app settings to grant it.""".trimMargin()

        override val normalDecline: String
            get() = "This app needs access to your fine location so it can track you better"

}