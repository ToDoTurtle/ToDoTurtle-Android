package com.eps.todoturtle.preferences.logic.data

import android.content.Context

interface PreferenceEnum<T> {
    fun getString(context: Context): String
}