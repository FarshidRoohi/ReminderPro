package io.github.farshidroohi.common.designsystem.pixelperfect

import android.content.Context
import androidx.compose.runtime.Composable

class PixelPerfectSize {
    val designSmallestWidth = 360F
    var smallestScreenWidth = 0
        private set

    var isDeviceTablet = false
        private set

    @Composable
    fun initializePixelPerfect() {
        smallestScreenWidth = getSmallestScreenWidth()
        isDeviceTablet = isDeviceTablet(smallestScreenWidth)
    }

    fun initializePixelPerfect(context: Context) {
        val config = context.resources.configuration
        smallestScreenWidth = config.smallestScreenWidthDp
        isDeviceTablet = isDeviceTablet(smallestScreenWidth)
    }
}
