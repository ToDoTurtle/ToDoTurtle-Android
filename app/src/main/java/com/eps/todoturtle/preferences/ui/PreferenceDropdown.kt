package com.eps.todoturtle.preferences.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.preferences.logic.data.PreferenceEnum
import com.eps.todoturtle.shared.logic.extensions.RobotoThin

@Composable
inline fun <reified T: PreferenceEnum<T>> PreferenceDropdown(
    @DrawableRes icon: Int,
    @StringRes iconDesc: Int,
    @StringRes text: Int,
    selected: PreferenceEnum<T>,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = MaterialTheme.colorScheme.tertiaryContainer,
        shape = RoundedCornerShape(10)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = stringResource(id = iconDesc),
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = text),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = FontFamily.RobotoThin,
                        textAlign = TextAlign.Start,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Dropdown(
                    selected = selected,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T: PreferenceEnum<T>>Dropdown(
    selected: PreferenceEnum<T>,
) {
    val expanded = rememberSaveable { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = it },
    ) {
        DropdownTitle(
            modifier = Modifier
                .menuAnchor(),
            expanded = expanded.value,
            selected = selected,
        )
//        DropdownMenu(
//
//        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T: PreferenceEnum<T>> DropdownTitle(
    modifier : Modifier,
    expanded: Boolean,
    selected: PreferenceEnum<T>,
) {
    TextField(
        modifier = modifier,
        value = selected.getString(LocalContext.current),
        onValueChange = {},
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        readOnly = true,
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            textAlign = TextAlign.End,
        ),
        trailingIcon = {
            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
        }
    )
}