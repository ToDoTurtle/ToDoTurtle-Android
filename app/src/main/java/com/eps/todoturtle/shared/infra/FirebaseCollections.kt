package com.eps.todoturtle.shared.infra

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * The database scheme is as follows:
 * users -> {user_id} |-> devices
 *                    |-> actions
 *                    |-> todo notes
 *                    |-> done notes
 */
private const val USERS_COLLECTION = "users"
private const val DEVICES_COLLECTION = "devices"
private const val ACTIONS_COLLECTION = "actions"
private const val TODO_NOTES_COLLECTION = "todo-notes"
private const val DONE_NOTES_COLLECTION = "done-notes"

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

/**
 * Returns the current user's actions collection reference.
 */
fun getActionsCollection(): CollectionReference {
    val userCollection = getUserCollection()
    return userCollection.collection(ACTIONS_COLLECTION)
}

/**
 * Returns the current user's todo notes collection reference.
 */
fun getToDoNotesCollection(): CollectionReference {
    val userCollection = getUserCollection()
    return userCollection.collection(TODO_NOTES_COLLECTION)
}

/**
 * Returns the current user's done notes collection reference.
 */

fun getDoneNotesCollection(): CollectionReference {
    val userCollection = getUserCollection()
    return userCollection.collection(DONE_NOTES_COLLECTION)
}
