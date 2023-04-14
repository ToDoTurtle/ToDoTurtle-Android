package com.eps.todoturtle.nav.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.nav.logic.Destination
import com.eps.todoturtle.nav.logic.screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Drawer(
    drawerState: DrawerState,
    onItemClick: (Destination) -> Unit,
    toDoCount: Int,
    scope: CoroutineScope,
    content: @Composable () -> Unit,
) {
    val selectedItem = remember { mutableStateOf(screens[0]) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "ToDo Turtle",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                )
                Spacer(Modifier.height(12.dp))
                Divider(thickness = 1.dp)
                Spacer(Modifier.height(12.dp))
                screens.forEach { item ->
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                item.icon,
                                contentDescription = item.label,
                            )
                        },
                        label = { Text(item.label) },
                        badge = when (item.route) {
                            "notes" -> {
                                { Text(toDoCount.toString()) }
                            }
                            "devices" -> {
                                { Text("0") }
                            }
                            else -> {
                                null
                            }
                        },
                        selected = item == selectedItem.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem.value = item
                            onItemClick(item)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    )
                }
            }
        },
    ) {
        content()
    }
}
