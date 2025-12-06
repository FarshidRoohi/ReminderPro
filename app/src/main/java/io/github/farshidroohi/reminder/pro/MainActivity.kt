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
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import io.github.farshidroohi.reminder.pro.home.HomeScreen
import io.github.farshidroohi.reminder.pro.location.LocationScreen
import io.github.farshidroohi.reminder.pro.note.NoteScreen
import io.github.farshidroohi.reminder.pro.theme.Primary
import io.github.farshidroohi.reminder.pro.theme.PrimaryBackground
import io.github.farshidroohi.reminder.pro.theme.ReminderTheme
import io.github.farshidroohi.reminder.pro.theme.TabSelectedBG
import io.github.farshidroohi.reminder.pro.theme.Zinc500
import io.github.farshidroohi.reminder.pro.theme.Zinc950
import io.github.farshidroohi.reminder.pro.voice.VoiceScreen

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

    var currentDestination by remember { mutableStateOf(Destination.HOME) }
    var selectedDestination by rememberSaveable { mutableIntStateOf(currentDestination.ordinal) }

    Scaffold(modifier.background(PrimaryBackground), bottomBar = {
        NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
            Destination.entries.forEachIndexed { index, destination ->

                val isSelectedItem = selectedDestination == index
                NavigationBarItem(
                    isSelectedItem, onClick = {
                        selectedDestination = index
                        currentDestination = destination
                    }, icon = {
                        val icon = if (isSelectedItem) {
                            destination.selectIcon
                        } else {
                            destination.unSelectIcon
                        }

                        Icon(
                            modifier = Modifier,
                            painter = painterResource(icon),
                            contentDescription = destination.contentDescription,
                        )

                    }, label = {
                        Text(destination.label)
                    }, colors = NavigationBarItemColors(
                        selectedTextColor = Primary,
                        selectedIconColor = Primary,
                        selectedIndicatorColor = TabSelectedBG,
                        unselectedIconColor = Zinc500,
                        unselectedTextColor = Zinc500,
                        disabledIconColor = Color.LightGray,
                        disabledTextColor = Color.LightGray
                    )
                )
            }

        }
    }, topBar = {
        TopAppBar(title = {
            Text(
                "Reminder", fontWeight = FontWeight.ExtraBold, fontSize = 32.sp, color = Zinc950
            )
        }, actions = {
            IconButton(onClick = {

            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "Search",
                    tint = Zinc950
                )
            }

            IconButton(onClick = {

            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_settings),
                    contentDescription = "Settings",
                    tint = Zinc950
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
        when (currentDestination) {
            Destination.HOME -> HomeScreen(Modifier.padding(innerPadding))
            Destination.NOTE -> NoteScreen(Modifier.padding(innerPadding))
            Destination.LOCATION -> LocationScreen(Modifier.padding(innerPadding))
            Destination.VOICE -> VoiceScreen(Modifier.padding(innerPadding))

        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewReminderHome() {
    ReminderTheme {
        Dashboard(Modifier)
    }
}