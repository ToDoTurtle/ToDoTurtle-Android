package com.eps.todoturtle.profile.logic

import android.graphics.Bitmap
import com.eps.todoturtle.shared.logic.extensions.bitmapFrom
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class DetailsPictureStorage(
    userAuth: UserAuth,
) {
    private val storage = Firebase.storage
    private val customPictureRef = storage.reference.child("users/${userAuth.getUid()}/profilePicture.jpg")
    private val defaultPictureRef = storage.reference.child("default_pfp.jpg")

    suspend fun getProfileImage(): Bitmap {
        return bitmapFrom(
            try {
                customPictureRef.getBytes(Long.MAX_VALUE).await()
            } catch (exception: StorageException) {
                defaultPictureRef.getBytes(Long.MAX_VALUE).await()
            }
        )
    }
}