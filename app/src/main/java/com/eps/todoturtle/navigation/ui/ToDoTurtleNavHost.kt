package com.eps.todoturtle.navigation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.eps.todoturtle.invite.ui.InviteUI
import com.eps.todoturtle.nfc.logic.DevicesViewModel
import com.eps.todoturtle.nfc.logic.NfcWriteViewModel
import com.eps.todoturtle.nfc.ui.DeviceScreen
import com.eps.todoturtle.nfc.ui.WriteDevice
import com.eps.todoturtle.note.logic.NoteScreenViewModel
import com.eps.todoturtle.note.ui.NoteScreen
import com.eps.todoturtle.permissions.logic.PermissionRequester
import com.eps.todoturtle.permissions.logic.RequestPermissionContext
import com.eps.todoturtle.preferences.logic.data.AppPreferences
import com.eps.todoturtle.preferences.ui.PreferenceUI
import com.eps.todoturtle.profile.logic.ProfileViewModel
import com.eps.todoturtle.profile.ui.details.DetailsUI
import com.eps.todoturtle.profile.ui.login.LoginUI

private const val LOGIN = "login"
private const val PROFILE = "profile"
private const val NOTES = "notes"
private const val SETTINGS = "settings"
private const val WRITE_DEVICE = "write_device"
private const val INVITE = "invite"
private const val DEVICES_WRITE_SUCCESSFUL_PARAM = "write_successful"
private const val DEVICES = "devices/{$DEVICES_WRITE_SUCCESSFUL_PARAM}"
const val DEVICES_WRITE_SUCCESSFUL = "devices/true"
const val DEVICES_NORMAL = "devices/false"

@Composable
fun ToDoTurtleNavHost(
    navController: NavHostController,
    permissionRequester: PermissionRequester,
    shouldShowMenu: MutableState<Boolean>,
    noteScreenViewModel: NoteScreenViewModel,
    profileViewModel: ProfileViewModel,
    devicesViewModel: DevicesViewModel,
    nfcWriteViewModel: NfcWriteViewModel,
    dataStore: DataStore<AppPreferences>,
    hasCameraPermission: () -> Boolean,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = LOGIN,
        modifier = modifier,
    ) {
        login(navController, shouldShowMenu)
        profile(
            permissionRequester,
            navController,
            profileViewModel,
            shouldShowMenu,
            hasCameraPermission,
        )
        notes(noteScreenViewModel)
        devices(navController, devicesViewModel)
        writeDevice(navController, nfcWriteViewModel)
        settings(dataStore)
        invite()
    }
}

fun NavGraphBuilder.login(navController: NavHostController, shouldShowMenu: MutableState<Boolean>) {
    composable(LOGIN) {
        LoginUI(
            onSignInClick = {
                navController.navigateFromLogin(NOTES)
                shouldShowMenu.value = true
            },
        )
    }
}

fun NavGraphBuilder.profile(
    permissionRequester: PermissionRequester,
    navController: NavHostController,
    profileViewModel: ProfileViewModel,
    shouldShowMenu: MutableState<Boolean>,
    hasCameraPermission: () -> Boolean,
) {
    composable(PROFILE) {
        RequestPermissionContext(permissionRequester) {
            DetailsUI(
                onSignOutClick = {
                    navController.navigateSingleTopTo(LOGIN)
                    shouldShowMenu.value = false
                },
                requestPermissions = { requestPermissions() },
                hasPermissions = { hasCameraPermission() },
                profileViewModel = profileViewModel,
            )
        }
    }
}

fun NavGraphBuilder.notes(noteScreenViewModel: NoteScreenViewModel) {
    composable(NOTES) {
        NoteScreen(
            viewModel = noteScreenViewModel,
        )
    }
}

fun NavGraphBuilder.devices(navController: NavHostController, devicesViewModel: DevicesViewModel) {
    composable(
        DEVICES,
        arguments = listOf(navArgument(DEVICES_WRITE_SUCCESSFUL_PARAM) { type = NavType.BoolType }),
    ) {
        val newDeviceAdded = it.arguments?.getBoolean(DEVICES_WRITE_SUCCESSFUL_PARAM) ?: false
        DeviceScreen(devicesViewModel = devicesViewModel, navController, newDeviceAdded)
    }
}

@Composable
fun DeviceScreen(
    devicesViewModel: DevicesViewModel,
    navController: NavHostController,
    newDeviceAdded: Boolean = false,
) {
    var deviceAdded by rememberSaveable { mutableStateOf(newDeviceAdded) }
    DeviceScreen(
        devicesViewModel = devicesViewModel,
        newDeviceAdded = deviceAdded,
        onNewDeviceAddedOkay = { deviceAdded = false },
        onAddDevice = {
            navController.navigate(WRITE_DEVICE) {
                navController.graph.startDestinationRoute?.let { _ ->
                    popUpTo(DEVICES_NORMAL) {
                        saveState = true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        },
    )
}

fun NavGraphBuilder.writeDevice(
    navController: NavHostController,
    nfcWriteViewModel: NfcWriteViewModel,
) {
    composable(WRITE_DEVICE) {
        WriteDevice(
            viewModel = nfcWriteViewModel,
            onNfcNotSupported = { navController.navigateSingleTopTo(NOTES) },
            onTagLost = {
                nfcWriteViewModel.finishNfc()
                navController.navigateSingleTopTo(WRITE_DEVICE)
            },
            onTagNotWriteable = {
                nfcWriteViewModel.finishNfc()
                navController.navigateSingleTopTo(DEVICES_NORMAL)
            },
            unknownError = {
                nfcWriteViewModel.finishNfc()
                navController.navigateSingleTopTo(WRITE_DEVICE)
            },
            onWriteSuccessful = {
                nfcWriteViewModel.finishNfc()
                navController.navigate(DEVICES_WRITE_SUCCESSFUL)
            },
        )
    }
}

private fun NavGraphBuilder.settings(dataStore: DataStore<AppPreferences>) {
    composable(SETTINGS) {
        PreferenceUI(dataStore = dataStore)
    }
}

fun NavGraphBuilder.invite() {
    composable(INVITE) {
        InviteUI()
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id,
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

fun NavHostController.navigateFromLogin(route: String) {
    this.navigate(route) {
        popUpTo(0)
    }
}
