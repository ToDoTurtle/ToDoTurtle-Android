package com.eps.todoturtle.action.infra

import com.eps.todoturtle.action.logic.ActionRepository
import com.eps.todoturtle.action.logic.NoteAction
import com.eps.todoturtle.shared.infra.getActionsCollection
import com.eps.todoturtle.shared.logic.forms.Timestamp
import kotlinx.coroutines.tasks.await

private const val TITLE = "title"
private const val DESCRIPTION = "description"
private const val DEADLINE = "deadline"
private const val NOTIFICATION = "notification"
private const val GET_LOCATION = "getLocation"

class FirebaseActionRepository : ActionRepository {

    override suspend fun linkDeviceWithAction(deviceId: String, noteAction: NoteAction) {
        val actions = getActionsCollection()
        actions.document(deviceId).set(
            mapOf(
                TITLE to noteAction.title,
                DESCRIPTION to noteAction.description,
                DEADLINE to noteAction.deadline.toFirebaseTimestamp(),
                NOTIFICATION to noteAction.notification.toFirebaseTimestamp(),
                GET_LOCATION to noteAction.getLocation,
            ),
        ).await()
    }

    override suspend fun getActionForDeviceWithId(id: String): NoteAction? {
        val actions = getActionsCollection()
        val action = actions.document(id).get().await()
        if (!action.exists()) {
            return null
        }
        val title = action.getString(TITLE)!!
        val description = action.getString(DESCRIPTION)!!
        val deadline = action.getTimestamp(DEADLINE).toDomainTimestamp()
        val notification = action.getTimestamp(NOTIFICATION).toDomainTimestamp()
        val getLocation = action.getBoolean(GET_LOCATION)!!
        return NoteAction(title, description, deadline, notification, getLocation)
    }

    override suspend fun removeLinkForDevice(deviceId: String) {
        val actions = getActionsCollection()
        val action = actions.document(deviceId).get().await()
        if (action.exists()) {
            actions.document(deviceId).delete()
        }
    }

    override suspend fun getAll(): Map<String, NoteAction> {
        val actions = getActionsCollection()
        val actionDocs = actions.get().await()
        val actionMap = HashMap<String, NoteAction>()
        for (actionDoc in actionDocs) {
            val title = actionDoc.getString(TITLE)!!
            val description = actionDoc.getString(DESCRIPTION)!!
            val deadline = actionDoc.getTimestamp(DEADLINE).toDomainTimestamp()
            val notification = actionDoc.getTimestamp(NOTIFICATION).toDomainTimestamp()
            val getLocation = actionDoc.getBoolean(GET_LOCATION)!!
            actionMap[actionDoc.id] = NoteAction(title, description, deadline, notification, getLocation)
        }
        return actionMap
    }

    private fun Timestamp?.toFirebaseTimestamp(): com.google.firebase.Timestamp? {
        if (this == null) return null
        return com.google.firebase.Timestamp(this.time, 0)
    }

    private fun com.google.firebase.Timestamp?.toDomainTimestamp(): Timestamp? {
        if (this == null) return null
        return Timestamp(this.toDate().time)
    }
}
