package com.eps.todoturtle.invite.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eps.todoturtle.R
import com.eps.todoturtle.shared.logic.extensions.RobotoThin

@Composable
fun InviteUI() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth(0.5f),
//                    .offset(y = (-100).dp),
                painter =  painterResource(id = R.drawable.construction),
                contentDescription = "Prova",
                tint = Color.LightGray,
            )
            Column(
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Under construction",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontFamily = FontFamily.RobotoThin,
                    color = Color.LightGray,
                )
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth(),
                    text = "This section is still in development, wait a bit while we finish implementing it.",
                    style = MaterialTheme.typography.headlineSmall,
//                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.RobotoThin,
                    color = Color.LightGray,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreferenceUIPreview() {
    InviteUI()
}