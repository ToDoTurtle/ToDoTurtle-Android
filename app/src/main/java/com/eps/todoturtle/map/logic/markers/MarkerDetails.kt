package com.eps.todoturtle.map.logic.markers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable

data class MarkerDetails(
    val context: Context,
    val latitude: Double,
    val longitude: Double,
    @StringRes private val titleId: Int,
    @StringRes private val snippetId: Int,
    @DrawableRes private val iconId: Int,
    var title: String? = null,
    var snippet: String? = null,
    var icon: Drawable? = null,
) {
    init {
        icon = ContextCompat.getDrawable(context, iconId)!!
        val bitmap = icon!!.toBitmap()
        val resized = Bitmap.createScaledBitmap(bitmap, 75, 75, true)
        icon = resized.toDrawable(context.resources)

        title = context.resources.getString(titleId)
        snippet = context.resources.getString(snippetId)
    }
}
