package io.github.farshidroohi.common.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.github.farshidroohi.common.R
import io.github.farshidroohi.common.designsystem.pixelperfect.units.psp

val vazirmatnFontFamily = FontFamily(
    Font(R.font.vazirmatn_extralight, FontWeight.ExtraLight),
    Font(R.font.vazirmatn_light, FontWeight.Light),
    Font(R.font.vazirmatn_regular, FontWeight.Normal),
    Font(R.font.vazirmatn_medium, FontWeight.Medium),
    Font(R.font.vazirmatn_bold, FontWeight.Bold),
    Font(R.font.vazirmatn_extrabold, FontWeight.ExtraBold),
    Font(R.font.vazirmatn_semibold, FontWeight.SemiBold),
    Font(R.font.vazirmatn_black, FontWeight.Black),
    Font(R.font.vazirmatn_thin, FontWeight.Thin),
)


val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = vazirmatnFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.psp,
        lineHeight = 32.psp,
        letterSpacing = 0.psp

    ),
    displayMedium = TextStyle(
        fontFamily = vazirmatnFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.psp,
        lineHeight = 24.psp,
        letterSpacing = 0.1.psp

    ),
    bodyMedium = TextStyle(
        fontFamily = vazirmatnFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.psp,
        lineHeight = 17.psp,
        letterSpacing = 0.5.psp
    ),
    bodyLarge = TextStyle(
        fontFamily = vazirmatnFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.psp,
        lineHeight = 24.psp,
        letterSpacing = 0.5.psp
    ),
    titleMedium = TextStyle(
        fontFamily = vazirmatnFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.psp,
        lineHeight = 22.psp,
        letterSpacing = 0.1.psp
    ),
    titleLarge = TextStyle(
        fontFamily = vazirmatnFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.psp,
        lineHeight = 28.psp,
        letterSpacing = 0.psp
    ),
    labelMedium = TextStyle(
        fontFamily = vazirmatnFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.psp,
        lineHeight = 14.psp,
        letterSpacing = 0.5.psp
    ),
    labelSmall = TextStyle(
        fontFamily = vazirmatnFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.psp,
        lineHeight = 16.psp,
        letterSpacing = 0.5.psp
    )
)