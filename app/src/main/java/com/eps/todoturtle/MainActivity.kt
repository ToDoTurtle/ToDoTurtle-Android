package com.eps.todoturtle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eps.todoturtle.permissions.logic.PermissionRequester
import com.eps.todoturtle.permissions.logic.RequestPermissionContext
import com.eps.todoturtle.permissions.logic.providers.CameraPermissionProvider
import com.eps.todoturtle.profile.ui.details.DetailsUI
import com.eps.todoturtle.profile.ui.login.LoginUI
import com.eps.todoturtle.shared.logic.extensions.hasCameraPermission
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme

class MainActivity : ComponentActivity() {
    private val permissionsToRequest = listOf(
        CameraPermissionProvider(),
    )

    private lateinit var permissionRequester: PermissionRequester

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionRequester = PermissionRequester(this, permissionsToRequest)

        setContent {
            ToDoTurtleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "login") {
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
                    }
                }
            }
        }
    }
}
