package io.github.farshidroohi.reminder.pro

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import io.github.farshidroohi.reminder.pro.home.HomeScreen
import io.github.farshidroohi.reminder.pro.theme.Primary
import io.github.farshidroohi.reminder.pro.theme.PrimaryBackground
import io.github.farshidroohi.reminder.pro.theme.ReminderTheme

class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReminderTheme {
                Dashboard(modifier = Modifier.fillMaxSize())

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(modifier: Modifier) {

    val startDestination = Destination.HOME
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    Scaffold(modifier.background(PrimaryBackground), bottomBar = {
        NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {

            Destination.entries.forEachIndexed { index, destination ->
                NavigationBarItem(selectedDestination == index, onClick = {
                    selectedDestination = index
                }, icon = {
                    Icon(
                        painter = painterResource(destination.icon),
                        contentDescription = destination.contentDescription
                    )
                }, label = {
                    Text(destination.label)
                })
            }

        }
    }, topBar = {
        TopAppBar(title = {
            Text("Reminder", fontWeight = FontWeight.ExtraBold, fontSize = 32.sp)
        }, actions = {
            IconButton(onClick = {

            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "Search"
                )
            }

            IconButton(onClick = {

            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_settings),
                    contentDescription = "Settings"
                )
            }
        })
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {

            }, containerColor = Primary, shape = CircleShape
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_add),
                contentDescription = "Add",
                tint = Color.White
            )
        }
    }) { innerPadding ->
        HomeScreen(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewReminderHome() {
    ReminderTheme {
        Dashboard(Modifier)
    }
}