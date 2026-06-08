package io.github.farshidroohi.common

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Light",
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
    device = "id:pixel_7",
    showSystemUi = false,
)
@Preview(
    name = "Dark",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true,
    device = "id:pixel_7",
    showSystemUi = false,
)
@Preview(
    name = "Light Desktop",
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
    device = "id:desktop_small",
    showSystemUi = false,
)
annotation class CustomPreview

