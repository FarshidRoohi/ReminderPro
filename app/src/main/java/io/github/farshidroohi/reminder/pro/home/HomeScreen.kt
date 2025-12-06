package io.github.farshidroohi.reminder.pro.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.farshidroohi.reminder.pro.R
import io.github.farshidroohi.reminder.pro.theme.PrimaryBackground
import io.github.farshidroohi.reminder.pro.theme.ReminderTheme
import io.github.farshidroohi.reminder.pro.theme.Zinc900
import io.github.farshidroohi.reminder.pro.theme.gradientColors

@Composable
fun HomeScreen(modifier: Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .background(PrimaryBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            "Welcome!",
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            color = Zinc900,
            modifier = Modifier
                .padding(top = 40.dp)
        )
        Text(
            stringResource(R.string.empty_state_message),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Zinc900,
            modifier = Modifier
                .padding(top = 16.dp)
        )

        val contentPadding = remember {
            PaddingValues(horizontal = 12.dp, vertical = 8.dp)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedButton(
                onClick = {},
                contentPadding = contentPadding,
                border = BorderStroke(
                    width = 1.dp,
                    brush = Brush.linearGradient(gradientColors)
                ),
                modifier = Modifier
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_voice_outline),
                        contentDescription = "",
                        modifier = Modifier.size(16.dp)
                    )
                    Text(text = stringResource(R.string.record_voice), fontSize = 12.sp)
                }

            }

            OutlinedButton(
                onClick = {},
                contentPadding = contentPadding,
                border = BorderStroke(
                    width = 1.dp,
                    brush = Brush.linearGradient(gradientColors)
                ),
                modifier = Modifier
                    .padding(horizontal = 2.dp)
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_location_outline),
                        contentDescription = "",
                        modifier = Modifier.size(16.dp)
                    )
                    Text(text = stringResource(R.string.add_location), fontSize = 12.sp)
                }

            }

            OutlinedButton(
                onClick = {},
                contentPadding = contentPadding,
                border = BorderStroke(
                    width = 1.dp,
                    brush = Brush.linearGradient(gradientColors)
                ),
                modifier = Modifier
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_note_outline),
                        contentDescription = "",
                        modifier = Modifier.size(16.dp)
                    )
                    Text(text = stringResource(R.string.new_note), fontSize = 12.sp)
                }

            }

        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    ReminderTheme {
        HomeScreen(Modifier)
    }
}