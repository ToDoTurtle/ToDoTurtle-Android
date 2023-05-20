package com.eps.todoturtle.permissions.logic.providers.text

import android.content.Context
import com.eps.todoturtle.R

class FineLocationTextProvider(val context: Context) : PermissionTextProvider {

    override val isPermanentlyDeclined: String
        get() = context.getString(R.string.fine_permanently_declined)
    override val normalDecline: String
        get() = context.getString(R.string.fine_permision_reason)
}
