package com.eps.todoturtle.nav.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eps.todoturtle.note.logic.NoteScreenViewModel
import com.eps.todoturtle.note.ui.NoteScreen
import com.eps.todoturtle.permissions.logic.PermissionRequester
import com.eps.todoturtle.permissions.logic.RequestPermissionContext
import com.eps.todoturtle.profile.ui.details.DetailsUI
import com.eps.todoturtle.profile.ui.login.LoginUI


@Composable
fun ToDoTurtleNavHost(
    navController: NavHostController,
    permissionRequester: PermissionRequester,
    noteScreenViewModel: NoteScreenViewModel,
    hasCameraPermission: () -> Boolean,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") { LoginUI(navController) }
        composable("profile") {
            RequestPermissionContext(permissionRequester) {
                DetailsUI(
                    navController = navController,
                    requestPermissions = { requestPermissions() },
                    hasPermissions = { hasCameraPermission() },
                )
            }
        }
        composable("notes") { NoteScreen(noteScreenViewModel) }
    }
}