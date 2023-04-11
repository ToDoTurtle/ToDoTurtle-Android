@file:OptIn(ExperimentalMaterial3Api::class)

package com.eps.todoturtle.ui.profile.details

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.profile.HostageType

@Composable
fun HostageTypeProfileField(
    hostageType: MutableState<HostageType>,
) {
    val expandedDropdown = rememberSaveable { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expandedDropdown.value,
        modifier = Modifier.padding(top = 15.dp),
        onExpandedChange = { expandedDropdown.value = !expandedDropdown.value },
    ) {
        HostageTypeDropdownTitle(
            modifier = Modifier.menuAnchor(),
            hostageType,
            expandedDropdown.value,
        )
        HostageTypeDropdownMenu(hostageType, expandedDropdown, this)
    }
}

@Composable
private fun HostageTypeDropdownTitle(
    modifier: Modifier,
    hostageType: MutableState<HostageType>,
    expandedDropdown: Boolean,
) {
    OutlinedTextField(
        modifier = modifier,
        readOnly = true,
        value = hostageType.value.getString(LocalContext.current),
        onValueChange = {},
        label = { Text(stringResource(id = R.string.hostage)) },
        trailingIcon = {
            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown)
        },
    )
}

@Composable
private fun HostageTypeDropdownMenu(
    hostageType: MutableState<HostageType>,
    expandedDropdown: MutableState<Boolean>,
    scope: ExposedDropdownMenuBoxScope,
) {
    scope.ExposedDropdownMenu(
        expanded = expandedDropdown.value,
        modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer),
        onDismissRequest = { expandedDropdown.value = false },
    ) {
        HostageType.values().forEach { type ->
            HostageTypeDropdownItem(type, hostageType, expandedDropdown)
        }
    }
}

@Composable
private fun HostageTypeDropdownItem(
    type: HostageType,
    hostageType: MutableState<HostageType>,
    expandedDropdown: MutableState<Boolean>,
) {
    DropdownMenuItem(text = {
        Text(
            text = type.getString(
                LocalContext.current,
            ),
        )
    }, onClick = {
        hostageType.value = type
        expandedDropdown.value = false
    })
}
