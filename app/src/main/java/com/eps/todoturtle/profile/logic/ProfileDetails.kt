package com.eps.todoturtle.profile.logic

import android.graphics.Bitmap

data class ProfileDetails(
    var username: String,
    var mail: String,
    val hostage: HostageType,
    val profilePicture: Bitmap,
)
