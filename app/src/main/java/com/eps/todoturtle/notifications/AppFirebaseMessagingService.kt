package com.eps.todoturtle.notifications

import com.eps.todoturtle.shared.infra.addDeviceToken
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class AppFirebaseMessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        if (Firebase.auth.currentUser == null) return
        runBlocking(Dispatchers.IO) {
            addDeviceToken(token)
        }
    }

}