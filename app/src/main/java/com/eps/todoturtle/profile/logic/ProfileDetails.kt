package com.eps.todoturtle.profile.logic

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState

data class ProfileDetails(
    var username: MutableState<String>,
    val hostage: MutableState<HostageType>,
    val profilePicture: MutableState<Bitmap>,
)
