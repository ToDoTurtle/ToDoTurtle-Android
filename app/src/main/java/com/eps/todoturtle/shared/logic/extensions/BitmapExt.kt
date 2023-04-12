package com.eps.todoturtle.shared.logic.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.eps.todoturtle.shared.logic.exceptions.DrawableNotFoundException

fun bitmapFrom(drawableId: Int, context: Context): Bitmap {
    val drawable = ContextCompat.getDrawable(context, drawableId)
    drawable ?: throw DrawableNotFoundException("Drawable with id $drawableId not found")
    val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}
