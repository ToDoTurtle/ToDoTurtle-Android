package com.eps.todoturtle.navigation.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.navigation.logic.Destination
import com.eps.todoturtle.navigation.logic.Devices
import com.eps.todoturtle.navigation.logic.Invite
import com.eps.todoturtle.navigation.logic.Notes
import com.eps.todoturtle.navigation.logic.Profile
import com.eps.todoturtle.navigation.logic.Settings

@Composable
fun Drawer(
    drawerState: DrawerState,
    onItemClick: (Destination) -> Unit,
    toDoCount: Int,
    devicesCount: Int,
    selectedItem: Destination,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = stringResource(id = R.string.spaced_app_name),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                )
                Spacer(Modifier.height(12.dp))
                Divider(thickness = 1.dp)
                Spacer(Modifier.height(12.dp))
                NotesItem(
                    isSelected = selectedItem == Notes,
                    onItemClick = { onItemClick(Notes) },
                    badge = toDoCount.toString(),
                )
                DevicesItem(
                    isSelected = selectedItem == Devices,
                    onItemClick = { onItemClick(Devices) },
                    badge = devicesCount.toString(),
                )
                ProfileItem(
                    isSelected = selectedItem == Profile,
                    onItemClick = { onItemClick(Profile) },
                )
                SettingsItem(
                    isSelected = selectedItem == Settings,
                    onItemClick = { onItemClick(Settings) },
                )
                AboutUsItem(
                    isSelected = selectedItem == Invite,
                    onItemClick = { onItemClick(Invite) },
                )
            }
        },
    ) {
        content()
    }
}

@Composable
fun NotesItem(
    isSelected: Boolean,
    onItemClick: () -> Unit,
    badge: String,
) {
    val label = stringResource(Notes.labelId)
    NavigationDrawerItem(
        icon = { Icon(ImageVector.vectorResource(id = R.drawable.note), label) },
        label = { Text(label) },
        selected = isSelected,
        onClick = onItemClick,
        badge = { Text(badge) },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
    )
}

@Composable
fun DevicesItem(
    isSelected: Boolean,
    onItemClick: () -> Unit,
    badge: String,
) {
    val label = stringResource(Devices.labelId)
    NavigationDrawerItem(
        icon = { Icon(ImageVector.vectorResource(id = R.drawable.devices), label) },
        label = { Text(label) },
        selected = isSelected,
        onClick = onItemClick,
        badge = { Text(badge) },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
    )
}

@Composable
fun ProfileItem(
    isSelected: Boolean,
    onItemClick: () -> Unit,
) {
    val label = stringResource(Profile.labelId)
    NavigationDrawerItem(
        icon = { Icon(Icons.Filled.Person, label) },
        label = { Text(label) },
        selected = isSelected,
        onClick = onItemClick,
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
    )
}

@Composable
fun SettingsItem(
    isSelected: Boolean,
    onItemClick: () -> Unit,
) {
    val label = stringResource(Settings.labelId)
    NavigationDrawerItem(
        icon = { Icon(Icons.Filled.Settings, label) },
        label = { Text(label) },
        selected = isSelected,
        onClick = onItemClick,
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
    )
}

@Composable
fun AboutUsItem(
    isSelected: Boolean,
    onItemClick: () -> Unit,
) {
    val label = stringResource(Invite.labelId)
    NavigationDrawerItem(
        icon = { Icon(Icons.Filled.Info, label) },
        label = { Text(label) },
        selected = isSelected,
        onClick = onItemClick,
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
    )
}
