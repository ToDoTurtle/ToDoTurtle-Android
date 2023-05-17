package com.eps.todoturtle.devices.infra

import com.eps.todoturtle.devices.logic.DeviceRepository
import com.eps.todoturtle.devices.logic.NFCDevice
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

private const val USERS_COLLECTION = "users"
private const val DEVICES_COLLECTION = "devices"

private const val ID_DEVICE = "id"
private const val NAME_DEVICE = "name"
private const val DESCRIPTION_DEVICE = "description"
private const val ICON_RES_ID_DEVICE = "iconResId"

class FirebaseDeviceRepository : DeviceRepository {

    override suspend fun getAll(): Collection<NFCDevice> {
        val devices = getDevicesCollection()
        return devices.get().await().map { document: QueryDocumentSnapshot ->
            val id = document.getString(ID_DEVICE)!!
            val name = document.getString(NAME_DEVICE)!!
            val description = document.getString(DESCRIPTION_DEVICE)!!
            val iconResId = document.getLong(ICON_RES_ID_DEVICE)!!.toInt()
            NFCDevice(id, name, description, iconResId)
        }.toCollection(mutableListOf())
    }

    override suspend fun add(device: NFCDevice) {
        val devices = getDevicesCollection()
        devices.add(
            mapOf(
                ID_DEVICE to device.identifier,
                NAME_DEVICE to device.name,
                DESCRIPTION_DEVICE to device.description,
                ICON_RES_ID_DEVICE to device.iconResId
            )
        ).await()
    }

    override suspend fun remove(device: NFCDevice) {
        val devices = getDevicesCollection()
        val documentToDelete = devices.whereEqualTo(ID_DEVICE, device.identifier).get().await()
        documentToDelete.forEach { document ->
            devices.document(document.id).delete()
        }
    }

    private fun getDevicesCollection(): CollectionReference {
        val uid = Firebase.auth.currentUser!!.uid
        val store = Firebase.firestore
        val userStorage = store.collection(USERS_COLLECTION).document(uid)
        return userStorage.collection(DEVICES_COLLECTION)
    }

}