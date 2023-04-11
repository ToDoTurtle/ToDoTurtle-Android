package com.eps.todoturtle.permissions.providers.text

class ReadExternalStorageTextProvider: PermissionTextProvider {
    override val isPermanentlyDeclined: String
        get() = """It seems you permanently declined permission to read external storage.
                |You can go to app settings to grant it.
        """.trimMargin()
    override val normalDecline: String
        get() = """This app needs access to read you external storage so that you can change the profile picture."""

}