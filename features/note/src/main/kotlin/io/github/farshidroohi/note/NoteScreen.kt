package io.github.farshidroohi.note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.EditNote
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
fun NoteScreen(modifier: Modifier = Modifier) {

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
            icon = Icons.Outlined.EditNote,
            title = stringResource(R.string.note_empty_state_message),
            btnIcon = Icons.Outlined.Edit,
            btnTitle = stringResource(R.string.add_note),
            onClick = {

            }
        )

    }
}

@Composable
fun MainContent(modifier: Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.pdp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {


    }
}

@CustomPreview
@Composable
fun Preview_NoteScreen() {
    AppTheme {
        NoteScreen(modifier = Modifier)
    }
}