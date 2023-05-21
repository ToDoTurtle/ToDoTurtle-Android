package com.eps.todoturtle.profile.logic

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.eps.todoturtle.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking

class ProfileViewModel(
    private val context: Context,
    userId: String,
) : ViewModel() {
    private var storage: DetailsPictureStorage
    private var _details: MutableStateFlow<ProfileDetails>

    init {
        storage = DetailsPictureStorage(userId)
        runBlocking {
            _details = MutableStateFlow(
                ProfileDetails(
                    username = context.getString(R.string.profile_default_username),
                    mail = context.getString(R.string.profile_default_username),
                    hostage = HostageType.FIRE_HOSTAGE,
                    profilePicture = storage.getProfileImage(),
                ),
            )
        }
    }

    val details: StateFlow<ProfileDetails> = _details.asStateFlow()

    fun changeUsername(newUsername: String) {
        _details.update { currentDetails ->
            currentDetails.copy(
                username = newUsername,
            )
        }
    }

    fun changeMail(newMail: String) {
        _details.update { currentDetails ->
            currentDetails.copy(
                mail = newMail,
            )
        }
    }

    fun changeHostageType(newHostageType: HostageType) {
        _details.update { currentDetails ->
            currentDetails.copy(
                hostage = newHostageType,
            )
        }
        Toast.makeText(
            context,
            context.getString(R.string.hostage_type_not_implemented),
            Toast.LENGTH_SHORT,
        ).show()
    }

    fun changeProfilePicture(newProfilePicture: Bitmap) {
        runBlocking(Dispatchers.IO) {
            storage.setProfileImage(newProfilePicture)
            _details.update { currentDetails ->
                currentDetails.copy(
                    profilePicture = newProfilePicture,
                )
            }
        }
    }
}
