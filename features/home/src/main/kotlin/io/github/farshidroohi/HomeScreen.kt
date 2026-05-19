package io.github.farshidroohi

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.farshidroohi.common.CustomPreview
import io.github.farshidroohi.common.designsystem.AnimatedBorderBox
import io.github.farshidroohi.common.designsystem.makeRandomColorList
import io.github.farshidroohi.common.designsystem.pixelperfect.units.pdp
import io.github.farshidroohi.common.ui.theme.AppTheme
import io.github.farshidroohi.common.ui.theme.PrimarySecondary
import io.github.farshidroohi.home.R

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
//            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.pdp)
            .fillMaxSize()
    ) {

        LazyColumn(Modifier) {
            items(10) {
                Card() {
                    Text(
                        "Hello $it",
                        Modifier
                            .fillMaxWidth()
                            .padding(12.pdp)
                            .clip(MaterialTheme.shapes.medium)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.pdp))
        WelcomeSection()

        Spacer(modifier = Modifier.height(16.pdp))
        QuickActionsRow()
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
            iconTintColor = PrimarySecondary,
        )
    }

}


@Composable
private fun WelcomeSection() {
    Column {
        Text(
            text = stringResource(R.string.welcome_title),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.surface
        )
        Spacer(modifier = Modifier.height(16.pdp))
        Text(
            text = stringResource(R.string.welcome_description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
private fun QuickActionsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        QuickActionButton(
            text = stringResource(R.string.action_record_voice),
            icon = Icons.Default.Mic,
            modifier = Modifier.weight(1f)
        )
        QuickActionButton(
            text = stringResource(R.string.action_add_location),
            icon = Icons.Default.LocationOn,
            modifier = Modifier.weight(1f)
        )
        QuickActionButton(
            text = stringResource(R.string.action_new_note),
            icon = Icons.Default.EditNote,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun QuickActionButton(
    text: String, icon: ImageVector, modifier: Modifier = Modifier
) {
    AnimatedBorderBox(
        modifier = modifier
            .height(40.pdp)
            .padding(4.pdp),
        colors = makeRandomColorList(),
    ) {
        Row(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .clickable {

                }
                .padding(vertical = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(16.pdp),
                tint = MaterialTheme.colorScheme.surface
            )
            Spacer(modifier = Modifier.width(4.pdp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.surface,
                maxLines = 2
            )
        }
    }
}

@Composable
private fun ReminderSection(
    title: String, emptyText: String, iconTintColor: Color, icon: ImageVector
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
//                color = MaterialTheme.colorScheme.surface
            )
        }
        Spacer(modifier = Modifier.height(12.pdp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.pdp))
                .background(MaterialTheme.colorScheme.outline),
        ) {
            Text(
                text = emptyText,
                modifier = Modifier.padding(16.pdp),
                fontSize = 16.sp,
//                color = MaterialTheme.colorScheme.surface
            )
        }
    }
}

@CustomPreview
@Composable
fun Preview_HomeScreen() {
    AppTheme(dynamicColor = false) {
        HomeScreen()
    }
}
