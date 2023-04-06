package com.eps.todoturtle.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.runtime.MutableState
import androidx.core.content.ContextCompat

data class ProfileDetails(var username: MutableState<String>, val hostage: MutableState<HostageType>, val profilePicture: MutableState<Bitmap>) {
    companion object {
        fun bitmapFrom(drawableId: Int, context: Context): Bitmap {
            val drawable = ContextCompat.getDrawable(context, drawableId)
            val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)  // FIXME: Remove null check
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }
    }
}
