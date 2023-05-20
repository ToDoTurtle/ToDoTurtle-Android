package com.eps.todoturtle.shared.infra

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

/**
 * The database scheme is as follows:
 * users -> {user_id} |-> devices
 *                    |-> actions
 *                    |-> todo notes
 *                    |-> done notes
 *                    |-> devices-tokens -> DEVICE_LIST -> { "devices": [device1, device2, ...] }
 */
private const val USERS_COLLECTION = "users"
private const val DEVICES_COLLECTION = "devices"
private const val ACTIONS_COLLECTION = "actions"
private const val TODO_NOTES_COLLECTION = "todo-notes"
private const val DONE_NOTES_COLLECTION = "done-notes"
private const val DEVICE_TOKENS_DOCUMENT = "devices-tokens"
private const val DEVICE_TOKENS_LIST = "DEVICE_LIST"
private const val DEVICE_TOKENS_KEY = "devices"

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

suspend fun getDevicesTokens(): MutableList<Any?> {
    val userCollection = getUserCollection()
    val deviceTokensDocument =
        userCollection.collection(DEVICE_TOKENS_DOCUMENT).document(DEVICE_TOKENS_LIST).get().await()
    val devices = (deviceTokensDocument.get(DEVICE_TOKENS_KEY) as List<*>?)
    if (devices == null) return mutableListOf()
    else return devices.toMutableList()
}

private suspend fun updateDevicesTokens(devices: MutableList<Any?>) {
    val userCollection = getUserCollection()
    val deviceTokensDocument =
        userCollection.collection(DEVICE_TOKENS_DOCUMENT).document(DEVICE_TOKENS_LIST)
    deviceTokensDocument.set(mapOf(DEVICE_TOKENS_KEY to devices)).await()
}

/**
 * Adds the device token to the current user's device tokens list for push notification support
 */
suspend fun addDeviceToken(token: String) {
    val devicesTokens = getDevicesTokens()
    if (!devicesTokens.contains(token)) {
        devicesTokens.add(token)
        updateDevicesTokens(devicesTokens)
    }
}