package com.eps.todoturtle.navigation.ui

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.eps.todoturtle.LoginActivity
import com.eps.todoturtle.aboutus.ui.AboutUsUI
import com.eps.todoturtle.action.logic.ActionViewModel
import com.eps.todoturtle.devices.logic.DEVICE_CONFIGURATION
import com.eps.todoturtle.devices.logic.DEVICE_CONFIGURATION_ID
import com.eps.todoturtle.devices.logic.DEVICE_CONFIGURATION_PARAM
import com.eps.todoturtle.devices.logic.DeviceConfigurationParams
import com.eps.todoturtle.devices.logic.DevicesViewModel
import com.eps.todoturtle.devices.ui.DeviceConfigurationScreen
import com.eps.todoturtle.devices.ui.DeviceScreen
import com.eps.todoturtle.navigation.logic.DEVICE_WRITE_SUCCESSFUL_PARAMETER
import com.eps.todoturtle.navigation.logic.Destinations
import com.eps.todoturtle.network.logic.NetworkAvailability
import com.eps.todoturtle.nfc.logic.NfcWriteViewModel
import com.eps.todoturtle.nfc.ui.WriteDevice
import com.eps.todoturtle.note.logic.NotesViewModel
import com.eps.todoturtle.note.logic.location.LocationClient
import com.eps.todoturtle.note.ui.NoteScreen
import com.eps.todoturtle.permissions.logic.PermissionRequester
import com.eps.todoturtle.permissions.logic.RequestPermissionContext
import com.eps.todoturtle.preferences.logic.data.AppPreferences
import com.eps.todoturtle.preferences.ui.PreferenceUI
import com.eps.todoturtle.profile.logic.ProfileViewModel
import com.eps.todoturtle.profile.logic.UserAuth
import com.eps.todoturtle.profile.ui.details.DetailsUI
import kotlinx.coroutines.flow.Flow

@Composable
fun ToDoTurtleNavHost(
    locationClient: LocationClient,
    hasLocationPermission: () -> Boolean,
    locationPermissionRequester: PermissionRequester,
    navController: NavHostController,
    cameraPermissionRequester: PermissionRequester,
    noteScreenViewModel: NotesViewModel,
    profileViewModel: ProfileViewModel,
    devicesViewModel: DevicesViewModel,
    nfcWriteViewModel: NfcWriteViewModel,
    actionsViewModel: ActionViewModel,
    dataStore: DataStore<AppPreferences>,
    hasCameraPermission: () -> Boolean,
    modifier: Modifier = Modifier,
    userAuth: UserAuth,
    connectionAvailability: Flow<NetworkAvailability>,
    onGoSettingsClick: () -> Unit,
    onCloseAppClick: () -> Unit,
    reloadActivity: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.NOTES.route,
        modifier = modifier,
    ) {
        profile(
            cameraPermissionRequester,
            profileViewModel,
            userAuth,
            hasCameraPermission,
        )
        notes(
            noteScreenViewModel,
            locationClient,
            locationPermissionRequester,
            hasLocationPermission,
            onGoSettingsClick,
            connectionAvailability,
            onCloseAppClick
        )
        devices(
            navController,
            devicesViewModel,
            actionsViewModel,
        )
        writeDevice(navController, nfcWriteViewModel)
        deviceConfiguration(devicesViewModel, navController)
        settings(dataStore, reloadActivity)
        invite()
    }
}

fun NavGraphBuilder.profile(
    permissionRequester: PermissionRequester,
    profileViewModel: ProfileViewModel,
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
                val intent = Intent(userAuth.getActivityContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                userAuth.getActivityContext().startActivity(intent)
            }
        }
    }
}

fun NavGraphBuilder.notes(
    noteScreenViewModel: NotesViewModel,
    locationClient: LocationClient,
    locationPermissionRequester: PermissionRequester,
    hasLocationPermission: () -> Boolean,
    onGoSettingsClick: () -> Unit,
    connectionAvailability: Flow<NetworkAvailability>,
    onCloseAppClick: () -> Unit
) {
    composable(Destinations.NOTES.route) {
        NoteScreen(
            viewModel = noteScreenViewModel,
            locationClient = locationClient,
            locationPermissionRequester = locationPermissionRequester,
            hasLocationPermission = hasLocationPermission,
            connectionAvailability = connectionAvailability,
            onGoToSettingsClick = onGoSettingsClick,
            onCloseAppClick = onCloseAppClick
        )
    }
}

fun NavGraphBuilder.devices(
    navController: NavHostController,
    devicesViewModel: DevicesViewModel,
    actionsViewModel: ActionViewModel,
) {
    composable(
        Destinations.DEVICES.route,
        arguments = listOf(
            navArgument(DEVICE_WRITE_SUCCESSFUL_PARAMETER) {
                type = NavType.BoolType
            },
        ),
    ) {
        val newDeviceAdded = it.arguments?.getBoolean(DEVICE_WRITE_SUCCESSFUL_PARAMETER) ?: false
        DeviceScreen(
            devicesViewModel = devicesViewModel,
            navController = navController,
            newDeviceAdded = newDeviceAdded,
            actionsViewModel = actionsViewModel,
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
            navArgument(DEVICE_CONFIGURATION_ID) { type = NavType.StringType },
        ),
    ) {
        val configurationType = DeviceConfigurationParams.fromString(
            it.arguments?.getString(DEVICE_CONFIGURATION_PARAM),
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
    navController: NavHostController,
    newDeviceAdded: Boolean = false,
    actionsViewModel: ActionViewModel,
) {
    var deviceAdded by rememberSaveable { mutableStateOf(newDeviceAdded) }
    DeviceScreen(
        devicesViewModel = devicesViewModel,
        newDeviceAdded = deviceAdded,
        actionViewModel = actionsViewModel,
        onEditDevice = {
            navController.navigateSingleTopTo(DeviceConfigurationParams.EDIT.getUri(it.identifier))
        },
        onNewDeviceAddedOkay = { deviceAdded = false },
        onAddDevice = {
            navController.navigateSingleTopTo(Destinations.WRITE_DEVICE.route)
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
                val uri = DeviceConfigurationParams.NEW.getUri(id)
                navController.navigateSingleTopTo(uri)
            },
        )
    }
}

private fun NavGraphBuilder.settings(
    dataStore: DataStore<AppPreferences>,
    reloadActivity: () -> Unit
) {
    composable(Destinations.SETTINGS.route) {
        PreferenceUI(dataStore = dataStore, reloadActivity = reloadActivity)
    }
}

fun NavGraphBuilder.invite() {
    composable(Destinations.ABOUT_US.route) {
        AboutUsUI()
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            route,
        ) {
        }
        launchSingleTop = true
    }
