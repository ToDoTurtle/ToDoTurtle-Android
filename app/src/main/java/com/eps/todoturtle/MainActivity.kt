package com.eps.todoturtle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.eps.todoturtle.permissions.PermissionRequester
import com.eps.todoturtle.permissions.RequestPermissionContext
import com.eps.todoturtle.permissions.providers.CameraPermissionProvider
import com.eps.todoturtle.ui.MainActivityUI
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
                RequestPermissionContext(permissionRequester) {
                    requestPermissions()
                    MainActivityUI()
                }
            }
        }
    }
}
