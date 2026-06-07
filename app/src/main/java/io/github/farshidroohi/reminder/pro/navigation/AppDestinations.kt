package io.github.farshidroohi.reminder.pro.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.farshidroohi.reminder.pro.R

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    HOME("Home", Icons.Default.Home),
    NOTE("Note", Icons.Default.EditNote),
    VOICE("Voice", Icons.Default.Mic),
    LOCATION("Location", Icons.Default.LocationOn), ;


    @StringRes
    fun toTitleOfAppBar(): Int {
        return when (this) {
            HOME -> R.string.app_name
            NOTE -> R.string.app_bar_title_note
            VOICE -> R.string.app_bar_title_voice
            LOCATION -> R.string.app_bar_title_location
        }
    }
}