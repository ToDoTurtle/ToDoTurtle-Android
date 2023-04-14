package com.eps.todoturtle.preferences.logic.data

import kotlinx.serialization.Serializable

@Serializable
data class AppPreferences(
    val mute: Boolean = false,
    val theme: Themes = Themes.Amber,
)
