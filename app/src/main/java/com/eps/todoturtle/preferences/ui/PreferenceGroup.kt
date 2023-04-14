package com.eps.todoturtle.preferences.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
internal fun PreferenceGroup(
    @StringRes groupTitle: Int,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(4)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = stringResource(groupTitle),
                modifier = Modifier.padding(horizontal = 6.dp),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(4)
            ) {
                Column {
                    content()
                }
            }
        }
    }
}
