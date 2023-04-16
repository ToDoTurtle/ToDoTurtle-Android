package com.eps.todoturtle.profile.logic

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.eps.todoturtle.R
import com.eps.todoturtle.shared.logic.extensions.bitmapFrom
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel(context: Context) : ViewModel() {
    private val _details = MutableStateFlow(
        ProfileDetails(
            username = context.getString(R.string.profile_default_username),
            mail = context.getString(R.string.profile_default_username),
            hostage = HostageType.FIRE_HOSTAGE,
            profilePicture = bitmapFrom(R.drawable.stickman2_pfp, context),
        ),
    )

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
    }

    fun changeProfilePicture(newProfilePicture: Bitmap) {
        _details.update { currentDetails ->
            currentDetails.copy(
                profilePicture = newProfilePicture,
            )
        }
    }
}
