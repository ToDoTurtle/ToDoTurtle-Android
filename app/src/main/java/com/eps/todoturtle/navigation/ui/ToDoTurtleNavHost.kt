package com.eps.todoturtle.navigation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eps.todoturtle.note.logic.NoteScreenViewModel
import com.eps.todoturtle.note.ui.NoteScreen
import com.eps.todoturtle.permissions.logic.PermissionRequester
import com.eps.todoturtle.permissions.logic.RequestPermissionContext
import com.eps.todoturtle.preferences.logic.data.AppPreferences
import com.eps.todoturtle.preferences.ui.PreferenceUI
import com.eps.todoturtle.profile.logic.ProfileViewModel
import com.eps.todoturtle.profile.ui.details.DetailsUI
import com.eps.todoturtle.profile.ui.login.LoginUI

@Composable
fun ToDoTurtleNavHost(
    navController: NavHostController,
    permissionRequester: PermissionRequester,
    shouldShowMenu: MutableState<Boolean>,
    noteScreenViewModel: NoteScreenViewModel,
    profileViewModel: ProfileViewModel,
    dataStore: DataStore<AppPreferences>,
    hasCameraPermission: () -> Boolean,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = "notes",
        modifier = modifier,
    ) {
        composable("login") {
            LoginUI(
                onSignInClick = {
                    navController.navigateSingleTopTo("notes")
                    shouldShowMenu.value = true
                },
            )
        }
        composable("profile") {
            RequestPermissionContext(permissionRequester) {
                DetailsUI(
                    onSignOutClick = {
                        navController.navigateSingleTopTo("login")
                        shouldShowMenu.value = false
                    },
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
