@file:OptIn(ExperimentalMaterial3Api::class)

package com.eps.todoturtle.profile.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.profile.logic.HostageType

@Composable
fun HostageTypeProfileField(
    hostageType: HostageType,
    onClick: (HostageType) -> Unit = {},
) {
    var expandedDropdown by rememberSaveable { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expandedDropdown,
        modifier = Modifier.padding(top = 15.dp),
        onExpandedChange = { expandedDropdown = !expandedDropdown },
    ) {
        HostageTypeDropdownTitle(
            modifier = Modifier.menuAnchor(),
            hostageType,
            expandedDropdown,
        )
        HostageTypeDropdownMenu(
            expandedDropdown = expandedDropdown,
            scope = this@ExposedDropdownMenuBox,
            onDismiss = { expandedDropdown = false },
        ) {
            onClick(it)
        }
    }
}

@Composable
private fun HostageTypeDropdownTitle(
    modifier: Modifier,
    hostageType: HostageType,
    expandedDropdown: Boolean,
) {
    OutlinedTextField(
        modifier = modifier,
        readOnly = true,
        value = hostageType.getString(LocalContext.current),
        onValueChange = {},
        label = { Text(stringResource(id = R.string.profile_hostage)) },
        trailingIcon = {
            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown)
        },
    )
}

@Composable
private fun HostageTypeDropdownMenu(
    expandedDropdown: Boolean,
    scope: ExposedDropdownMenuBoxScope,
    onDismiss: () -> Unit = {},
    onClick: (HostageType) -> Unit = {},
) {
    scope.ExposedDropdownMenu(
        expanded = expandedDropdown,
        modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer),
        onDismissRequest = { onDismiss() },
    ) {
        HostageType.values().forEach { type ->
            HostageTypeDropdownItem(type) {
                onClick(it)
                onDismiss()
            }
        }
    }
}

@Composable
private fun HostageTypeDropdownItem(
    type: HostageType,
    onClick: (HostageType) -> Unit = {},
) {
    DropdownMenuItem(
        text = {
            Text(
                text = type.getString(
                    LocalContext.current,
                ),
            )
        },
        onClick = {
            onClick(type)
        },
    )
}
