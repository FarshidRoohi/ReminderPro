package io.github.farshidroohi.common.designsystem.pixelperfect

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun getSmallestScreenWidth() = LocalConfiguration.current.smallestScreenWidthDp

fun isDeviceTablet(smallestScreenWidthDp: Int) = smallestScreenWidthDp > 600

