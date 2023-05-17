package com.eps.todoturtle.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.datastore.core.DataStore
import androidx.navigation.compose.rememberNavController
import com.eps.todoturtle.action.logic.ActionViewModel
import com.eps.todoturtle.devices.logic.DevicesViewModel
import com.eps.todoturtle.navigation.logic.Destination
import com.eps.todoturtle.navigation.logic.Devices
import com.eps.todoturtle.navigation.logic.Invite
import com.eps.todoturtle.navigation.logic.Notes
import com.eps.todoturtle.navigation.logic.Profile
import com.eps.todoturtle.navigation.logic.Settings
import com.eps.todoturtle.navigation.ui.Drawer
import com.eps.todoturtle.navigation.ui.ToDoTurtleNavHost
import com.eps.todoturtle.navigation.ui.TopBar
import com.eps.todoturtle.navigation.ui.navigateSingleTopTo
import com.eps.todoturtle.network.logic.NetworkAvailability
import com.eps.todoturtle.nfc.logic.NfcWriteViewModel
import com.eps.todoturtle.note.logic.NotesViewModelInt
import com.eps.todoturtle.note.logic.location.LocationClient
import com.eps.todoturtle.permissions.logic.PermissionRequester
import com.eps.todoturtle.preferences.logic.data.AppPreferences
import com.eps.todoturtle.profile.logic.ProfileViewModel
import com.eps.todoturtle.profile.logic.UserAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    hasLocationPermision: () -> Boolean,
    locationClient: LocationClient,
    locationPermissionRequester: PermissionRequester,
    cameraPermissionRequester: PermissionRequester,
    noteScreenViewModel: NotesViewModelInt,
    profileViewModel: ProfileViewModel,
    actionsViewModel: ActionViewModel,
    devicesViewModel: DevicesViewModel,
    nfcWriteViewModel: NfcWriteViewModel,
    dataStore: DataStore<AppPreferences>,
    hasCameraPermission: () -> Boolean,
    userAuth: UserAuth,
    connectionAvailability: Flow<NetworkAvailability>
) {
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        DrawerContainer(
            drawerState = drawerState,
            toDoCount = noteScreenViewModel.toDoNotes.size,
            devicesCount = devicesViewModel.getDevices().size,
            onItemClick = { destination ->
                scope.launch { drawerState.close() }
                navController.navigateSingleTopTo(destination.route)
            },
        ) {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopBar(
                        onMenuClick = { scope.launch { drawerState.open() } },
                    )
                },
            ) { innerPadding ->
                ToDoTurtleNavHost(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                    hasLocationPermission = { hasLocationPermision() },
                    locationClient = locationClient,
                    locationPermissionRequester = locationPermissionRequester,
                    cameraPermissionRequester = cameraPermissionRequester,
                    noteScreenViewModel = noteScreenViewModel,
                    devicesViewModel = devicesViewModel,
                    actionsViewModel = actionsViewModel,
                    nfcWriteViewModel = nfcWriteViewModel,
                    dataStore = dataStore,
                    profileViewModel = profileViewModel,
                    hasCameraPermission = { hasCameraPermission() },
                    userAuth = userAuth,
                    connectionAvailability = connectionAvailability,
                )
            }
        }
    }
}

@Composable
fun DrawerContainer(
    drawerState: DrawerState,
    toDoCount: Int,
    devicesCount: Int,
    onItemClick: (Destination) -> Unit,
    content: @Composable () -> Unit,
) {
    val items = listOf(Notes, Devices, Profile, Settings, Invite)
    var selectedItem by remember { mutableStateOf(items[0]) }
    Drawer(
        drawerState = drawerState,
        toDoCount = toDoCount,
        devicesCount = devicesCount,
        onItemClick = { destination ->
            selectedItem = destination
            onItemClick(destination)
        },
        selectedItem = selectedItem,
    ) {
        content()
    }
}
