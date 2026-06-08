package io.github.farshidroohi.common.designsystem.pixelperfect.units

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.farshidroohi.common.designsystem.pixelperfect.PixelPerfectSize

const val WARNING_TAG = "PIXEL-PERFECT"
val pixelPerfectSize = PixelPerfectSize()

@Stable
inline val Float.pdp: Dp
    get() {
        val screenWidth = pixelPerfectSize.smallestScreenWidth

        if (screenWidth <= 0) {
            Log.w(WARNING_TAG, "Pixel Perfect is not initialized !")
            return this.dp
        }
        return this.toPdp(pixelPerfectSize)
    }

@Stable
inline val Int.pdp: Dp
    get() = this.toFloat().pdp

@Stable
inline val Double.pdp: Dp
    get() = this.toFloat().pdp

fun Float.toPdp(pixelPerfectSize: PixelPerfectSize): Dp {
    val smallestScreenWidth = pixelPerfectSize.smallestScreenWidth
    val designSmallestWidth = pixelPerfectSize.designSmallestWidth
    val isDeviceTablet = pixelPerfectSize.isDeviceTablet

    val pixelPerfectFactor = smallestScreenWidth.toFloat() / designSmallestWidth
    val tabletFactor = if (isDeviceTablet) 0.8F else 1F // for tablet designWidth is not always full Width
    return (this * pixelPerfectFactor * tabletFactor).dp
}
