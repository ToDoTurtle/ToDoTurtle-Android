package com.eps.todoturtle.notifications

import com.eps.todoturtle.shared.infra.addDeviceToken
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class AppFirebaseMessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        runBlocking(Dispatchers.IO) {
            addDeviceToken(token)
        }
    }

}