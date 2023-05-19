package com.eps.todoturtle.action.infra

import com.eps.todoturtle.action.logic.ActionRepository
import com.eps.todoturtle.action.logic.NoteAction

class InMemoryActionRepository : ActionRepository {

    val actions = HashMap<String, NoteAction>()

    override suspend fun linkDeviceWithAction(deviceId: String, noteAction: NoteAction) {
        actions[deviceId] = noteAction
    }

    override suspend fun getActionForDeviceWithId(id: String): NoteAction? {
        return actions[id]
    }

    override suspend fun removeLinkForDevice(deviceId: String) {
        if (deviceId !in actions) return
        actions.remove(deviceId)
    }

    override suspend fun getAll(): Map<String, NoteAction> = actions
}
