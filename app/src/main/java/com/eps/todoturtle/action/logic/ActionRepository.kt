package com.eps.todoturtle.action.logic

interface ActionRepository {
    suspend fun linkDeviceWithAction(deviceId: String, noteAction: NoteAction)
    suspend fun getActionForDeviceWithId(id: String): NoteAction?
    suspend fun removeLinkForDevice(deviceId: String)
}
