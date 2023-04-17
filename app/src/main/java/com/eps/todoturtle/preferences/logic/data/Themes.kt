package com.eps.todoturtle.preferences.logic.data

import android.content.Context
import com.eps.todoturtle.R

enum class Themes : PreferenceEnum<Themes> {
    Greenish,
    Cold,
    ColdDark,
    Amber,
    Magenta,
    Default,
    ;

    override fun enumValues() = Themes.values()

    override fun getValue() = this

    override fun getString(context: Context) =
        when (this) {
            Greenish -> context.getString(R.string.preference_theme_greenish)
            Cold -> context.getString(R.string.preference_theme_cold)
            ColdDark -> context.getString(R.string.preference_theme_cold_dark)
            Amber -> context.getString(R.string.preference_theme_amber)
            Magenta -> context.getString(R.string.preference_theme_magenta)
            Default -> context.getString(R.string.preference_theme_default)
        }
}
