package com.eps.todoturtle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.compose.rememberNavController
import com.eps.todoturtle.nav.ui.Drawer
import com.eps.todoturtle.nav.ui.TopBar
import com.eps.todoturtle.nav.ui.screens
import com.eps.todoturtle.note.logic.NoteScreenViewModel
import com.eps.todoturtle.note.ui.NoteScreen
import com.eps.todoturtle.permissions.logic.PermissionRequester
import com.eps.todoturtle.permissions.logic.providers.CameraPermissionProvider
import com.eps.todoturtle.ui.theme.ToDoTurtleTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val permissionsToRequest = listOf(
        CameraPermissionProvider(),
    )

    private lateinit var permissionRequester: PermissionRequester

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionRequester = PermissionRequester(this, permissionsToRequest)

        val viewModel = NoteScreenViewModel()

        setContent {
            ToDoTurtleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navController = rememberNavController()
                    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                    val drawerState = rememberDrawerState(DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    Drawer(
                        drawerState = drawerState,
                        scope = scope,
                    ) {
                        Scaffold(
                            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                            topBar = {
                                TopBar(onMenuClick = { scope.launch { drawerState.open() } })
                            }) { innerPadding ->
                            NoteScreen(
                                modifier = Modifier.padding(innerPadding),
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
