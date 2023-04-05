package com.eps.todoturtle.pfp

import android.content.Context
import android.os.Parcelable
import com.eps.todoturtle.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class HostageType : Parcelable {
    SELF_HOSTAGE,
    FIRE_HOSTAGE;

    fun getString(context: Context): String {
        return when (this) {
            SELF_HOSTAGE -> context.getString(R.string.self_hosted)
            FIRE_HOSTAGE -> context.getString(R.string.firebase)
        }
    }
}
