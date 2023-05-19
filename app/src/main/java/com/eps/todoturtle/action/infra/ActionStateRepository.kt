package com.eps.todoturtle.action.infra

import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.eps.todoturtle.action.logic.ActionRepository
import com.eps.todoturtle.action.logic.NoteAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class ActionStateRepository(val repository: ActionRepository) : ActionRepository {

    val actionLinkDevice: SnapshotStateMap<String, NoteAction> = SnapshotStateMap()

    init {
        actionLinkDevice.putAll(getAllFromRepository())
    }

    override suspend fun linkDeviceWithAction(deviceId: String, noteAction: NoteAction) {
        actionLinkDevice[deviceId] = noteAction
        repository.linkDeviceWithAction(deviceId, noteAction)
    }

    override suspend fun getActionForDeviceWithId(id: String) = actionLinkDevice.get(id)

    override suspend fun removeLinkForDevice(deviceId: String) {
        actionLinkDevice.remove(deviceId)
        repository.removeLinkForDevice(deviceId)
    }

    override suspend fun getAll() = actionLinkDevice.toMap()

    private fun getAllFromRepository() = runBlocking(Dispatchers.IO) { repository.getAll() }
}
