package com.eps.todoturtle.profile.logic

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState

data class ProfileDetails(
    var username: String,
    var mail: String,
    val hostage: HostageType,
    val profilePicture: Bitmap,
)
