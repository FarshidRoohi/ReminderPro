package io.github.farshidroohi.common.designsystem.pixelperfect.units

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Stable
inline val Float.psp: TextUnit
    get() = this.pdp.value.sp

@Stable
val Double.psp: TextUnit
    get() = this.toFloat().psp

@Stable
val Int.psp: TextUnit
    get() = this.toFloat().psp

@Stable
inline val TextStyle.pspFontSize: TextStyle
    get() {
        return this.copy(fontSize = fontSize.value.psp, lineHeight = lineHeight.value.psp)
    }
