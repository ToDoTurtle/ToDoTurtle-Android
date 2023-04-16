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

private const val LOGIN = "login"
private const val PROFILE = "profile"
private const val NOTES = "notes"
private const val DEVICES = "devices"
private const val SETTINGS = "settings"
private const val INVITE = "invite"

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
        startDestination = LOGIN,
        modifier = modifier,
    ) {
        composable(LOGIN) {
            LoginUI(
                onSignInClick = {
                    navController.navigateSingleTopTo(NOTES)
                    shouldShowMenu.value = true
                },
            )
        }
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
        composable(NOTES) {
            NoteScreen(
                viewModel = noteScreenViewModel,
            )
        }
        composable(DEVICES) {}
        composable(SETTINGS) {
            PreferenceUI(dataStore = dataStore)
        }
        composable(INVITE) {}
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
