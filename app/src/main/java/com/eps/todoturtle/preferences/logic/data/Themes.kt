package com.eps.todoturtle.preferences.logic.data

import android.content.Context
import com.eps.todoturtle.R

enum class Themes: PreferenceEnum<Themes> {
    Greenish,
    Cold,
    ColdDark,
    Amber,
    Magenta,
    ;

    override fun getString(context: Context) =
        when (this) {
            Greenish -> context.getString(R.string.theme_greenish)
            Cold -> context.getString(R.string.theme_cold)
            ColdDark -> context.getString(R.string.theme_cold_dark)
            Amber -> context.getString(R.string.theme_amber)
            Magenta -> context.getString(R.string.theme_magenta)
        }
}