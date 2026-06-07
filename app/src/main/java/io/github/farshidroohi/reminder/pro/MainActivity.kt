package io.github.farshidroohi.reminder.pro

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import io.github.farshidroohi.HomeScreen
import io.github.farshidroohi.common.CustomPreview
import io.github.farshidroohi.common.designsystem.pixelperfect.units.pdp
import io.github.farshidroohi.common.ui.theme.AppTheme
import io.github.farshidroohi.home.R
import io.github.farshidroohi.location.LocationScreen
import io.github.farshidroohi.note.NoteScreen
import io.github.farshidroohi.reminder.pro.navigation.AppDestinations
import io.github.farshidroohi.voice.VoiceScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Surface(Modifier.fillMaxSize()) {
                    ReminderApp()
                }
            }
        }
    }
}

@Composable
fun ReminderApp(modifier: Modifier = Modifier) {

    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
    val state = rememberNavigationSuiteScaffoldState()

    val adaptiveInfo = currentWindowAdaptiveInfo()
    val customNavSuiteType = with(adaptiveInfo) {
        if (windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND)) {
            NavigationSuiteType.NavigationDrawer
        } else {
            NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
        }
    }

    val myNavigationSuiteItemColors = NavigationSuiteDefaults.itemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
            selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
    )

    NavigationSuiteScaffold(
        state = state,
        layoutType = customNavSuiteType,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        navigationSuiteItems = {
            AppDestinations.entries.forEach { destinations ->
                item(
                    icon = { Icon(destinations.icon, destinations.name) },
                    label = { Text(destinations.label) },
                    selected = destinations == currentDestination,
                    onClick = { currentDestination = destinations },
                    colors = myNavigationSuiteItemColors
                )
            }
        }
    ) {

        Scaffold(
            modifier = modifier.fillMaxSize(),
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { },
                    containerColor = Color(0xFF1B2233),
                    contentColor = Color.White,
                    shape = CircleShape,
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.cd_add_reminder)
                    )
                }
            }) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                AppTopBar(currentDestination.toTitleOfAppBar())

                when (currentDestination) {
                    AppDestinations.HOME -> HomeScreen()
                    AppDestinations.NOTE -> NoteScreen()
                    AppDestinations.VOICE -> VoiceScreen()
                    AppDestinations.LOCATION -> LocationScreen()
                }
            }
        }
    }
}

@Composable
private fun AppTopBar(@StringRes titleRes: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.pdp, vertical = 16.pdp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(titleRes),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Row {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.cd_search),
                    modifier = Modifier.size(24.pdp)
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.cd_settings),
                    modifier = Modifier.size(24.pdp)
                )
            }
        }
    }
}

@CustomPreview
@Composable
fun Preview_MainScreen() {
    AppTheme {
        ReminderApp()
    }
}