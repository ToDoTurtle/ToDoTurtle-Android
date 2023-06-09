package com.eps.todoturtle.profile.logic

import android.content.Context
import android.os.Parcelable
import com.eps.todoturtle.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class HostageType : Parcelable {
    SELF_HOSTAGE,
    FIRE_HOSTAGE,
    ;

    fun getString(context: Context): String {
        return when (this) {
            SELF_HOSTAGE -> context.getString(R.string.profile_self_hosted)
            FIRE_HOSTAGE -> context.getString(R.string.profile_firebase)
        }
    }
}
