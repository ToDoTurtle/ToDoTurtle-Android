package com.eps.todoturtle.profile.logic

import android.graphics.Bitmap
import com.eps.todoturtle.shared.logic.extensions.bitmapFrom
import com.eps.todoturtle.shared.logic.extensions.byteArrayFrom
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class DetailsPictureStorage(
    userId: String,
) {
    private val storage = Firebase.storage
    private val customPictureRef = storage.reference.child("users/$userId/profilePicture.jpg")
    private val defaultPictureRef = storage.reference.child("default_pfp.jpg")

    suspend fun getProfileImage(): Bitmap {
        return bitmapFrom(
            try {
                customPictureRef.getBytes(Long.MAX_VALUE).result
            } catch (ignored: Throwable) {
                defaultPictureRef.getBytes(Long.MAX_VALUE).await()
            },
        )
    }

    suspend fun setProfileImage(bitmap: Bitmap) {
        customPictureRef.putBytes(byteArrayFrom(bitmap)).await()
    }
}
