package com.eps.todoturtle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.eps.todoturtle.nfc.logic.NfcWriteViewModel.INIT.getDevicesViewModel
import com.eps.todoturtle.note.logic.NoteScreenViewModel
import com.eps.todoturtle.permissions.logic.PermissionRequester
import com.eps.todoturtle.permissions.logic.providers.CameraPermissionProvider
import com.eps.todoturtle.profile.logic.ProfileViewModel
import com.eps.todoturtle.shared.logic.extensions.dataStore
import com.eps.todoturtle.shared.logic.extensions.hasCameraPermission
import com.eps.todoturtle.ui.App
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme

class MainActivity : ComponentActivity() {
    private val permissionsToRequest = listOf(CameraPermissionProvider(this))
    private lateinit var permissionRequester: PermissionRequester

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionRequester = PermissionRequester(this, permissionsToRequest)

        val noteScreenViewModel = NoteScreenViewModel(this)
        val profileViewModel = ProfileViewModel(this)

        setContent {
            ToDoTurtleTheme {
                App(
                    permissionRequester = permissionRequester,
                    noteScreenViewModel = noteScreenViewModel,
                    devicesViewModel = getDevicesViewModel(),
                    profileViewModel = profileViewModel,
                    dataStore = dataStore,
                    hasCameraPermission = { hasCameraPermission() },
                )
            }
        }
    }
}
