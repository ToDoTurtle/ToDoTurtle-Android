package com.eps.todoturtle.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.eps.todoturtle.navigation.logic.Devices
import com.eps.todoturtle.nav.ui.Drawer
import com.eps.todoturtle.navigation.logic.Invite
import com.eps.todoturtle.navigation.logic.Notes
import com.eps.todoturtle.navigation.logic.Profile
import com.eps.todoturtle.navigation.logic.Settings
import com.eps.todoturtle.navigation.ui.ToDoTurtleNavHost
import com.eps.todoturtle.navigation.ui.TopBar
import com.eps.todoturtle.navigation.ui.navigateSingleTopTo
import com.eps.todoturtle.note.logic.NoteScreenViewModel
import com.eps.todoturtle.permissions.logic.PermissionRequester
import com.eps.todoturtle.preferences.logic.data.AppPreferences
import com.eps.todoturtle.profile.logic.ProfileViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    permissionRequester: PermissionRequester,
    noteScreenViewModel: NoteScreenViewModel,
    profileViewModel: ProfileViewModel,
    dataStore: DataStore<AppPreferences>,
    hasCameraPermission: () -> Boolean,
) {
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        val items = listOf(Notes, Devices, Profile, Settings, Invite)
        var selectedItem by remember { mutableStateOf(items.first()) }
        Drawer(
            drawerState = drawerState,
            toDoCount = noteScreenViewModel.toDoNotes.size,
            onItemClick = { destination ->
                selectedItem = destination
                scope.launch { drawerState.close() }
                navController.navigateSingleTopTo(destination.route)
            },
            selectedItem = selectedItem,
        ) {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopBar(onMenuClick = { scope.launch { drawerState.open() } })
                },
            ) { innerPadding ->
                ToDoTurtleNavHost(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                    permissionRequester = permissionRequester,
                    noteScreenViewModel = noteScreenViewModel,
                    dataStore = dataStore,
                    profileViewModel = profileViewModel,
                    hasCameraPermission = { hasCameraPermission() },
                )
            }
        }
    }
}
