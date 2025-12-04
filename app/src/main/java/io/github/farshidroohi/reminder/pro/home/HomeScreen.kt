package io.github.farshidroohi.reminder.pro.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.farshidroohi.reminder.pro.theme.ReminderTheme

@Composable
fun HomeScreen(modifier: Modifier) {
    Text("Hello")
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    ReminderTheme {
        HomeScreen(Modifier)
    }
}