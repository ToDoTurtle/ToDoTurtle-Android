package com.eps.todoturtle.profile.logic

import android.graphics.Bitmap
import com.eps.todoturtle.shared.logic.extensions.bitmapFrom
import com.eps.todoturtle.shared.logic.extensions.byteArrayFrom
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class DetailsPictureStorage(
    userId: String,
) {
    private val storage = Firebase.storage
    private val customPictureRef = storage.reference.child("users/$userId/profilePicture.jpg")
    private val defaultPictureRef = storage.reference.child("default_pfp.jpg")

    suspend fun getProfileImage(): Bitmap {
        return if (customExists()) {
            bitmapFrom(customPictureRef.getBytes(1024 * 1024).await())
        } else {
            bitmapFrom(defaultPictureRef.getBytes(1024 * 1024).await())
        }
    }

    private suspend fun customExists(): Boolean {
        return try {
            customPictureRef.metadata.await()
            true
        } catch (exception: StorageException) {
            false
        }
    }

    suspend fun setProfileImage(bitmap: Bitmap) {
        customPictureRef.putBytes(byteArrayFrom(bitmap)).await()
    }
}
