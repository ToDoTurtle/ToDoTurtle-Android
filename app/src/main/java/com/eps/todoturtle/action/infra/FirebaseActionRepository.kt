package com.eps.todoturtle.action.infra

import com.eps.todoturtle.action.logic.ActionRepository
import com.eps.todoturtle.action.logic.NoteAction

class FirebaseActionRepository: ActionRepository {

    override suspend fun linkDeviceWithAction(deviceId: String, noteAction: NoteAction) {
        TODO("Not yet implemented")
    }

    override suspend fun getActionForDeviceWithId(id: String): NoteAction? {
        TODO("Not yet implemented")
    }

    override suspend fun removeLinkForDevice(deviceId: String) {
        TODO("Not yet implemented")
    }

}