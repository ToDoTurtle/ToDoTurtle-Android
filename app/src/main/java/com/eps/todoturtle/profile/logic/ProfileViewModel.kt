package com.eps.todoturtle.profile.logic

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.eps.todoturtle.R
import com.eps.todoturtle.shared.logic.extensions.bitmapFrom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking

class ProfileViewModel(
    context: Context,
    userId: String,
) : ViewModel() {
    var currentDetails : ProfileDetails
    private var storage: DetailsPictureStorage
    private var profileDetailsChannel: Channel<ProfileDetails>
    init {
        storage = DetailsPictureStorage(userId)
        profileDetailsChannel = Channel(UNLIMITED)
        runBlocking(Dispatchers.IO) {
            val details = ProfileDetails(
                    username = context.getString(R.string.profile_default_username),
                    mail = context.getString(R.string.profile_default_username),
                    hostage = HostageType.FIRE_HOSTAGE,
                    profilePicture = storage.getProfileImage(),
                )
            profileDetailsChannel.send(
                details
            )
            currentDetails = details
        }
    }

    val profileDetailsFlow: Flow<ProfileDetails> = profileDetailsChannel.receiveAsFlow()
//    private val _details = MutableStateFlow(
//        ProfileDetails(
//            username = context.getString(R.string.profile_default_username),
//            mail = context.getString(R.string.profile_default_username),
//            hostage = HostageType.FIRE_HOSTAGE,
//            profilePicture = storage.getProfileImage(),
//        ),
//    )

//    val details: StateFlow<ProfileDetails> = _details.asStateFlow()

    fun changeUsername(newUsername: String) {
        runBlocking(Dispatchers.IO) {
            val newDetails = currentDetails.copy(
                username = newUsername,
            )
            profileDetailsChannel.send(
                newDetails
            )
            currentDetails = newDetails
        }
//        _details.update { currentDetails ->
//            currentDetails.copy(
//                username = newUsername,
//            )
//        }
    }

    fun changeMail(newMail: String) {
        runBlocking(Dispatchers.IO) {
            val newDetails = currentDetails.copy(
                mail = newMail,
            )
            profileDetailsChannel.send(
                newDetails
            )
            currentDetails = newDetails
        }
//        _details.update { currentDetails ->
//            currentDetails.copy(
//                mail = newMail,
//            )
//        }
    }

    fun changeHostageType(newHostageType: HostageType) {
        runBlocking(Dispatchers.IO) {
            val newDetails = currentDetails.copy(
                hostage = newHostageType,
            )
            profileDetailsChannel.send(
                newDetails
            )
            currentDetails = newDetails
        }
//        _details.update { currentDetails ->
//            currentDetails.copy(
//                hostage = newHostageType,
//            )
//        }
    }

    fun changeProfilePicture(newProfilePicture: Bitmap) {
        runBlocking(Dispatchers.IO) {
            val newDetails = currentDetails.copy(
                profilePicture = newProfilePicture,
            )
            profileDetailsChannel.send(
                newDetails
            )
            currentDetails = newDetails
            storage.setProfileImage(newProfilePicture)
        }
//        storage.setProfileImage(newProfilePicture)
//        _details.update { currentDetails ->
//            currentDetails.copy(
//                profilePicture = newProfilePicture,
//            )
//        }
    }
}
