package io.github.farshidroohi.reminder.pro.note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import io.github.farshidroohi.reminder.pro.theme.ReminderTheme
import io.github.farshidroohi.reminder.pro.theme.Zinc950

@Composable
fun NoteScreen(modifier: Modifier) {
    Column(
        modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hello From Note Screen", color = Zinc950)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    ReminderTheme {
        NoteScreen(Modifier)
    }
}