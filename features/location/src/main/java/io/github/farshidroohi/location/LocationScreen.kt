package io.github.farshidroohi.location

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.farshidroohi.common.CustomPreview
import io.github.farshidroohi.common.designsystem.EmptyState
import io.github.farshidroohi.common.designsystem.pixelperfect.units.pdp
import io.github.farshidroohi.common.ui.theme.AppTheme

@Composable
fun LocationScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.pdp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        EmptyState(
            modifier = Modifier.width(200.pdp),
            icon = Icons.Outlined.Mic,
            title = stringResource(R.string.location_empty_state_message),
            btnIcon = Icons.Outlined.Mic,
            btnTitle = stringResource(R.string.add_location),
            onClick = {

            }
        )

    }
}

@CustomPreview
@Composable
fun Preview_LocationScreen() {
    AppTheme {
        LocationScreen()
    }
}