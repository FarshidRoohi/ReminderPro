package io.github.farshidroohi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import io.github.farshidroohi.common.CustomPreview
import io.github.farshidroohi.common.designsystem.pixelperfect.units.pdp
import io.github.farshidroohi.common.ui.theme.AppTheme
import io.github.farshidroohi.home.R

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.pdp)
            .fillMaxSize()
    ) {

        Spacer(modifier = Modifier.height(40.pdp))
        WelcomeSection()

        Spacer(modifier = Modifier.height(32.pdp))
        ReminderSection(
            title = stringResource(R.string.section_today),
            emptyText = stringResource(R.string.empty_today),
            icon = Icons.Default.CalendarMonth,
            iconTintColor = MaterialTheme.colorScheme.tertiary,
        )
        Spacer(modifier = Modifier.height(24.pdp))
        ReminderSection(
            title = stringResource(R.string.section_overdue),
            emptyText = stringResource(R.string.empty_overdue),
            icon = Icons.Default.ErrorOutline,
            iconTintColor = MaterialTheme.colorScheme.error,
        )
        Spacer(modifier = Modifier.height(24.pdp))
        ReminderSection(
            title = stringResource(R.string.section_upcoming),
            emptyText = stringResource(R.string.empty_upcoming),
            icon = Icons.Default.EditCalendar,
            iconTintColor = MaterialTheme.colorScheme.tertiary,
        )
    }

}


@Composable
private fun WelcomeSection() {
    Column {
        Text(
            text = stringResource(R.string.welcome_title),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.pdp))
        Text(
            text = stringResource(R.string.welcome_description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ReminderSection(
    title: String,
    emptyText: String,
    iconTintColor: Color,
    icon: ImageVector,
) {
    Column(modifier = Modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(24.pdp),
                imageVector = icon,
                contentDescription = title,
                tint = iconTintColor
            )
            Spacer(modifier = Modifier.width(8.pdp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.height(12.pdp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.pdp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
        ) {
            Text(
                text = emptyText,
                modifier = Modifier.padding(16.pdp),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@CustomPreview
@Composable
fun Preview_HomeScreen() {
    AppTheme(dynamicColor = true) {
        HomeScreen()
    }
}
