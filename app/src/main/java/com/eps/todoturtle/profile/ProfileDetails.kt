package com.eps.todoturtle.profile

import androidx.compose.runtime.MutableState

data class ProfileDetails(var username: MutableState<String>, val hostage: MutableState<HostageType>, val profilePicture: MutableState<Int>)
