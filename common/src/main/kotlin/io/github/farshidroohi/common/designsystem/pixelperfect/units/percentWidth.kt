package io.github.farshidroohi.common.designsystem.pixelperfect.units

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
inline val Float.percentWidth: Dp
    get() {
        val smallestScreenWidth = pixelPerfectSize.smallestScreenWidth
        return (smallestScreenWidth * (this / 100)).dp
    }

@Stable
val Double.pdpPercentWidth: Dp
    get() = this.toFloat().percentWidth

@Stable
val Int.pdpPercentWidth: Dp
    get() = this.toFloat().percentWidth
