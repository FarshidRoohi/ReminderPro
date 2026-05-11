package io.github.farshidroohi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.farshidroohi.common.CustomPreview

@Composable
fun HomeScreen() {
    Box(contentAlignment = Alignment.Center) {
        Column {
            Text("Hello")
            Text("Word")
        }
        Row(modifier = Modifier.padding(top = 56.dp)) {
            Text("Hello ")
            Text("Word")
        }
    }


}

@CustomPreview
@Composable
fun Preview_HomeScreen() {
    HomeScreen()
}