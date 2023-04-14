package com.eps.todoturtle.nav.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eps.todoturtle.preferences.ui.PreferenceUI
import com.eps.todoturtle.note.logic.NoteScreenViewModel
import com.eps.todoturtle.note.ui.NoteScreen
import com.eps.todoturtle.permissions.logic.PermissionRequester
import com.eps.todoturtle.permissions.logic.RequestPermissionContext
import com.eps.todoturtle.preferences.logic.data.AppPreferences
import com.eps.todoturtle.profile.logic.ProfileViewModel
import com.eps.todoturtle.profile.ui.details.DetailsUI
import com.eps.todoturtle.profile.ui.login.LoginUI

@Composable
fun ToDoTurtleNavHost(
    navController: NavHostController,
    permissionRequester: PermissionRequester,
    noteScreenViewModel: NoteScreenViewModel,
    profileViewModel: ProfileViewModel,
    dataStore: DataStore<AppPreferences>,
    hasCameraPermission: () -> Boolean,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = "settings",  // FIXME: Change to "login" when pushing to master
        modifier = modifier,
    ) {
        composable("login") {
            LoginUI(
                onSignInClick = { navController.navigateSingleTopTo("notes") },
            )
        }
        composable("profile") {
            RequestPermissionContext(permissionRequester) {
                DetailsUI(
                    onSignOutClick = { navController.navigateSingleTopTo("login") },
                    requestPermissions = { requestPermissions() },
                    hasPermissions = { hasCameraPermission() },
                    profileViewModel = profileViewModel,
                )
            }
        }
        composable("notes") {
            NoteScreen(
                viewModel = noteScreenViewModel,
            )
        }
        composable("devices") {}
        composable("settings") {
            PreferenceUI(dataStore = dataStore)
        }
        composable("invite") {}
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
