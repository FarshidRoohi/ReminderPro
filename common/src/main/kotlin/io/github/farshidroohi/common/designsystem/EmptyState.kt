package io.github.farshidroohi.common.designsystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.farshidroohi.common.CustomPreview
import io.github.farshidroohi.common.designsystem.pixelperfect.units.pdp
import io.github.farshidroohi.common.ui.theme.AppTheme

@Composable
fun EmptyState(
    modifier: Modifier,
    icon: ImageVector,
    title: String,
    btnTitle: String,
    btnIcon: ImageVector,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Image(
            imageVector = icon, contentDescription = null,
            modifier = Modifier
                .size(136.pdp)
                .padding(vertical = 16.pdp)
        )

        Button(onClick, shape = CircleShape) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    imageVector = btnIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier
                        .padding(end = 8.pdp)
                )
                Text(
                    text = btnTitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiary
                )

            }
        }
    }
}


@CustomPreview
@Composable
fun Preview_EmptyView() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmptyState(
                modifier = Modifier.width(200.pdp),
                icon = Icons.Outlined.Image,
                title = "No Note Yet",
                btnTitle = "Add a note",
                btnIcon = Icons.Outlined.Edit,
                onClick = {

                })
        }
    }
}