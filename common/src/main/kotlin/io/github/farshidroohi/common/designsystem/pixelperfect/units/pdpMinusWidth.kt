package io.github.farshidroohi.common.designsystem.pixelperfect.units

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp

@Stable
inline val Float.pdpMinusWidth: Dp
    get() {
        val designSmallestWidth = pixelPerfectSize.designSmallestWidth
        return (designSmallestWidth - this).pdp
    }

@Stable
val Double.pdpMinusWidth: Dp
    get() = this.toFloat().pdpMinusWidth

@Stable
val Int.pdpMinusWidth: Dp
    get() = this.toFloat().pdpMinusWidth
