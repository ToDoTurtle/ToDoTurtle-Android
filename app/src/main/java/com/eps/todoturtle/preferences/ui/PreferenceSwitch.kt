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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eps.todoturtle.R
import com.eps.todoturtle.shared.logic.extensions.RobotoThin

@Composable
internal fun PreferenceSwitch(
    @DrawableRes icon: Int,
    @StringRes iconDesc: Int,
    @StringRes text: Int,
    checked: Boolean,
    @StringRes preferenceDescription: Int? = null,
    reload: (() -> Unit)? = null,
    onCheckedChange: (Boolean) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = RoundedCornerShape(percent = 10),
        onClick = {
            onCheckedChange(!checked)
        },
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = icon),
                        modifier = Modifier.size(40.dp),
                        contentDescription = stringResource(id = iconDesc),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Text(
                            text = stringResource(text),
                            style = MaterialTheme.typography.bodyLarge,
                            fontFamily = FontFamily.RobotoThin,
                            textAlign = TextAlign.Start,
                        )
                        preferenceDescription?.let {
                            Text(
                                text = stringResource(R.string.app_reload_warning),
                                modifier = Modifier.padding(top = 4.dp),
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = FontFamily.RobotoThin,
                                textAlign = TextAlign.Start,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = checked,
                    onCheckedChange = { isChecked ->
                        onCheckedChange(isChecked)
                        reload?.apply {
                            if (isChecked) invoke()
                        }
                    },
                )
            }
        }
    }
}

@Preview
@Composable
fun PreferenceSwitchPreview() {
    PreferenceSwitch(
        icon = android.R.drawable.ic_menu_preferences,
        iconDesc = R.string.preferences_only_wifi_desc,
        text = R.string.preferences_only_wifi_desc,
        preferenceDescription = R.string.app_reload_warning,
        checked = true,
        onCheckedChange = {},
    )
}
