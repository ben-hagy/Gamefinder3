package com.benhagy.gamefinder3.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.benhagy.gamefinder3.R

// Set of Material typography styles to start with

val montserratFonts = FontFamily(
    Font(R.font.montserrat_thin, weight = FontWeight.Thin),
    Font(R.font.montserrat_thinitalic, weight = FontWeight.Thin, style = FontStyle.Italic),
    Font(R.font.montserrat_extralight, weight = FontWeight.ExtraLight),
    Font(R.font.montserrat_extralightitalic, weight = FontWeight.ExtraLight, style = FontStyle.Italic),
    Font(R.font.montserrat_light, weight = FontWeight.Light),
    Font(R.font.montserrat_lightitalic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.montserrat_medium, weight = FontWeight.Medium),
    Font(R.font.montserrat_mediumitalic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.montserrat_semibold, weight = FontWeight.SemiBold),
    Font(R.font.montserrat_semibolditalic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
    Font(R.font.montserrat_bold, weight = FontWeight.Bold),
    Font(R.font.montserrat_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.montserrat_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.montserrat_extrabolditalic, weight = FontWeight.ExtraBold, style = FontStyle.Italic),
    Font(R.font.montserrat_black, weight = FontWeight.Black),
    Font(R.font.montserrat_blackitalic, weight = FontWeight.Black, style = FontStyle.Italic)
)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = montserratFonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = montserratFonts,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.7.sp
    ),
    titleLarge = TextStyle(
        fontFamily = montserratFonts,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.2.sp
    ),
    titleMedium = TextStyle(
        fontFamily = montserratFonts,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.2.sp
    ),
    titleSmall = TextStyle(
        fontFamily = montserratFonts,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.2.sp
    ),
    labelSmall = TextStyle(
        fontFamily = montserratFonts,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.5.sp
    ),
    displaySmall = TextStyle(
        fontFamily = montserratFonts,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.3.sp
    )
)