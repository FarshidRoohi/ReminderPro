package io.github.farshidroohi.common.designsystem

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

val gradientButtonColors = listOf(
    Color(0XFF88CCA6),
    Color(0XFF6DBACC),
    Color(0XFF52A7E5),
    Color(0XFF363795),
)

fun makeRandomColorList(): List<Color> {
    val seed = Random(System.currentTimeMillis())
    return gradientButtonColors.shuffled(seed)
}