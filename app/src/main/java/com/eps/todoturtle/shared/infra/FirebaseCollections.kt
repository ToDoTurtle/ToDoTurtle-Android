package com.eps.todoturtle.shared.infra

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val USERS_COLLECTION = "users"
private const val DEVICES_COLLECTION = "devices"

/**
 * Returns the current user collection reference.
 */
fun getUserCollection(): DocumentReference {
    val uid = Firebase.auth.currentUser!!.uid
    val store = Firebase.firestore
    return store.collection(USERS_COLLECTION).document(uid)
}

/**
 * Returns the current user's devices collection reference.
 */
fun getDevicesCollection(): CollectionReference {
    val userCollection = getUserCollection()
    return userCollection.collection(DEVICES_COLLECTION)
}
