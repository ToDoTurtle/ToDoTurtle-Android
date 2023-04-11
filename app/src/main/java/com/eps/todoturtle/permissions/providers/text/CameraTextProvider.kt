package com.eps.todoturtle.permissions.providers.text

class CameraTextProvider : PermissionTextProvider {
    override val isPermanentlyDeclined: String
        get() = """It seems you permanently declined permission to access your camera.
                |You can go to app settings to grant it.
        """.trimMargin()
    override val normalDecline: String
        get() = "This app needs access to your camera so that you can change the profile picture."
}
