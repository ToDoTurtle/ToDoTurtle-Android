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
import com.eps.todoturtle.devices.logic.DEVICE_CONFIGURATION
import com.eps.todoturtle.devices.logic.DEVICE_CONFIGURATION_ID
import com.eps.todoturtle.devices.logic.DEVICE_CONFIGURATION_PARAM
import com.eps.todoturtle.devices.logic.DEVICE_CONFIGURATION_PARAMS
import com.eps.todoturtle.devices.logic.DevicesViewModel
import com.eps.todoturtle.devices.ui.DeviceConfigurationScreen
import com.eps.todoturtle.devices.ui.DeviceScreen
import com.eps.todoturtle.invite.ui.InviteUI
import com.eps.todoturtle.navigation.logic.DEVICE_WRITE_SUCCESSFUL_PARAMETER
import com.eps.todoturtle.navigation.logic.Destinations
import com.eps.todoturtle.nfc.logic.NfcWriteViewModel
import com.eps.todoturtle.nfc.ui.WriteDevice
import com.eps.todoturtle.note.logic.NotesViewModelInt
import com.eps.todoturtle.note.ui.NoteScreen
import com.eps.todoturtle.permissions.logic.PermissionRequester
import com.eps.todoturtle.permissions.logic.RequestPermissionContext
import com.eps.todoturtle.preferences.logic.data.AppPreferences
import com.eps.todoturtle.preferences.ui.PreferenceUI
import com.eps.todoturtle.profile.logic.ProfileViewModel
import com.eps.todoturtle.profile.logic.UserAuth
import com.eps.todoturtle.profile.ui.details.DetailsUI
import com.eps.todoturtle.profile.ui.login.LoginUI

@Composable
fun ToDoTurtleNavHost(
    navController: NavHostController,
    permissionRequester: PermissionRequester,
    shouldShowMenu: MutableState<Boolean>,
    noteScreenViewModel: NotesViewModelInt,
    profileViewModel: ProfileViewModel,
    deviceScreenNoteViewModel: NotesViewModelInt,
    devicesViewModel: DevicesViewModel,
    nfcWriteViewModel: NfcWriteViewModel,
    dataStore: DataStore<AppPreferences>,
    hasCameraPermission: () -> Boolean,
    modifier: Modifier = Modifier,
    userAuth: UserAuth,
) {
    val startDestination = if (userAuth.isLoggedIn()) Destinations.NOTES else Destinations.LOGIN

    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier,
    ) {
        login(navController, shouldShowMenu, userAuth)
        profile(
            permissionRequester,
            navController,
            profileViewModel,
            shouldShowMenu,
            userAuth,
            hasCameraPermission,
        )
        notes(noteScreenViewModel)
        devices(navController, devicesViewModel, deviceScreenNoteViewModel)
        writeDevice(navController, nfcWriteViewModel)
        deviceConfiguration(devicesViewModel, navController)
        settings(dataStore)
        invite()
    }
}

fun NavGraphBuilder.login(
    navController: NavHostController,
    shouldShowMenu: MutableState<Boolean>,
    userAuth: UserAuth,
) {
    composable(Destinations.LOGIN.route) {
        LoginUI(userAuth = userAuth) {
            navController.navigateFromLogin(Destinations.NOTES.route)
            shouldShowMenu.value = true
        }
    }
}

fun NavGraphBuilder.profile(
    permissionRequester: PermissionRequester,
    navController: NavHostController,
    profileViewModel: ProfileViewModel,
    shouldShowMenu: MutableState<Boolean>,
    userAuth: UserAuth,
    hasCameraPermission: () -> Boolean,
) {
    composable(Destinations.PROFILE.route) {
        RequestPermissionContext(permissionRequester) {
            DetailsUI(
                requestPermissions = { requestPermissions() },
                hasPermissions = { hasCameraPermission() },
                profileViewModel = profileViewModel,
            ) {
                userAuth.logout()
                navController.navigateSingleTopTo(Destinations.LOGIN.route)
                shouldShowMenu.value = false
            }
        }
    }
}

fun NavGraphBuilder.notes(noteScreenViewModel: NotesViewModelInt) {
    composable(Destinations.NOTES.route) {
        NoteScreen(
            viewModel = noteScreenViewModel,
        )
    }
}

fun NavGraphBuilder.devices(
    navController: NavHostController,
    devicesViewModel: DevicesViewModel,
    deviceScreenNoteViewModel: NotesViewModelInt,
) {
    composable(
        Destinations.DEVICES.route,
        arguments = listOf(navArgument(DEVICE_WRITE_SUCCESSFUL_PARAMETER) {
            type = NavType.BoolType
        }),
    ) {
        val newDeviceAdded = it.arguments?.getBoolean(DEVICE_WRITE_SUCCESSFUL_PARAMETER) ?: false
        DeviceScreen(
            devicesViewModel = devicesViewModel,
            noteScreenViewModel = deviceScreenNoteViewModel,
            navController = navController,
            newDeviceAdded = newDeviceAdded,
        )
    }
}

fun NavGraphBuilder.deviceConfiguration(
    devicesViewModel: DevicesViewModel,
    navController: NavHostController,
) {
    composable(
        DEVICE_CONFIGURATION,
        arguments = listOf(
            navArgument(DEVICE_CONFIGURATION_PARAM) { type = NavType.StringType },
            navArgument(DEVICE_CONFIGURATION_ID) { type = NavType.StringType }
        ),
    ) {
        val configurationType = DEVICE_CONFIGURATION_PARAMS.fromString(
            it.arguments?.getString(DEVICE_CONFIGURATION_PARAM)
        )
        val deviceId: String = it.arguments?.getString(DEVICE_CONFIGURATION_ID)!!
        devicesViewModel.deviceBuilder.identifier = deviceId
        configurationType?.let {
            DeviceConfigurationScreen(devicesViewModel, it) {
                navController.navigateSingleTopTo(Destinations.DEVICES_WRITE_SUCCESSFUL.route)
            }
        }
    }
}

@Composable
fun DeviceScreen(
    devicesViewModel: DevicesViewModel,
    noteScreenViewModel: NotesViewModelInt,
    navController: NavHostController,
    newDeviceAdded: Boolean = false,
) {
    var deviceAdded by rememberSaveable { mutableStateOf(newDeviceAdded) }
    DeviceScreen(
        devicesViewModel = devicesViewModel,
        notesViewModel = noteScreenViewModel,
        newDeviceAdded = deviceAdded,
        onEditDevice = {
            navController.navigate(DEVICE_CONFIGURATION_PARAMS.EDIT.getUri(it.identifier)) {
                navController.graph.startDestinationRoute?.let { _ ->
                    popUpTo(Destinations.DEVICES_NORMAL.route) {
                        saveState = true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        onNewDeviceAddedOkay = { deviceAdded = false },
        onAddDevice = {
            navController.navigate(Destinations.WRITE_DEVICE.route) {
                navController.graph.startDestinationRoute?.let {
                    popUpTo(Destinations.DEVICES_NORMAL.route) {
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
    composable(Destinations.WRITE_DEVICE.route) {
        WriteDevice(
            viewModel = nfcWriteViewModel,
            onNfcNotSupported = { navController.navigateSingleTopTo(Destinations.NOTES.route) },
            onTagLost = {
                nfcWriteViewModel.finishWriteNfc()
                navController.navigateSingleTopTo(Destinations.WRITE_DEVICE.route)
            },
            onTagNotWriteable = {
                nfcWriteViewModel.finishWriteNfc()
                navController.navigateSingleTopTo(Destinations.DEVICES_NORMAL.route)
            },
            unknownError = {
                nfcWriteViewModel.finishWriteNfc()
                navController.navigateSingleTopTo(Destinations.WRITE_DEVICE.route)
            },
            onWriteSuccessful = { id ->
                nfcWriteViewModel.finishWriteNfc()
                navController.navigateSingleTopTo(DEVICE_CONFIGURATION_PARAMS.NEW.getUri(id))
            },
        )
    }
}

private fun NavGraphBuilder.settings(dataStore: DataStore<AppPreferences>) {
    composable(Destinations.SETTINGS.route) {
        PreferenceUI(dataStore = dataStore)
    }
}

fun NavGraphBuilder.invite() {
    composable(Destinations.INVITE.route) {
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
