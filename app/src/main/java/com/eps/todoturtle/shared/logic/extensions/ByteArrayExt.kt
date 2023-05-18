package com.eps.todoturtle.shared.logic.extensions

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

fun byteArrayFrom(bitmap: Bitmap): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}
