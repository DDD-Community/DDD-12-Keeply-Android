package com.keeply.presentation.core.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.keeply.presentation.R

// Font Families
private val AltformFontFamily = FontFamily(
    Font(R.font.altform_light, FontWeight.Light),
    Font(R.font.altform_regular, FontWeight.Normal),
    Font(R.font.altform_semi_bold, FontWeight.SemiBold)
)

private val SuitFontFamily = FontFamily(
    Font(R.font.suit_regular, FontWeight.Normal),
    Font(R.font.suit_medium, FontWeight.Medium),
    Font(R.font.suit_semi_bold, FontWeight.SemiBold)
)

// Base Text Styles
private val AltformStyle = TextStyle(
    fontFamily = AltformFontFamily,
)

private val SuitStyle = TextStyle(
    fontFamily = SuitFontFamily,
)

// Keeply Typography
internal val Typography = KeeplyTypography(
    // Altform Headers
    header01 = AltformStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = (32 * LINE_HEIGHT_RATIO_140).sp,
    ),
    header02 = AltformStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 26.sp,
        lineHeight = (26 * LINE_HEIGHT_RATIO_140).sp,
    ),
    header03 = AltformStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = (24 * LINE_HEIGHT_RATIO_100).sp,
    ),
    header04 = AltformStyle.copy(
        fontWeight = FontWeight.Light,
        fontSize = 22.sp,
        lineHeight = (22 * LINE_HEIGHT_RATIO_100).sp,
    ),
    header05 = AltformStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = (18 * LINE_HEIGHT_RATIO_140).sp,
    ),
    header06 = AltformStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = (14 * LINE_HEIGHT_RATIO_130).sp,
    ),
    
    // Suit Subtitles
    subtitle01 = SuitStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = (16 * LINE_HEIGHT_RATIO_140).sp,
    ),
    subtitle02 = SuitStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = (14 * LINE_HEIGHT_RATIO_140).sp,
    ),
    subtitle03 = SuitStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = (12 * LINE_HEIGHT_RATIO_140).sp,
    ),
    
    // Suit Body & Caption
    body = SuitStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = (14 * LINE_HEIGHT_RATIO_140).sp,
    ),
    caption01 = SuitStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = (14 * LINE_HEIGHT_RATIO_140).sp,
    ),
    caption02 = SuitStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = (12 * LINE_HEIGHT_RATIO_140).sp,
    ),

    // Button Styles
    button01Altform = AltformStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = (16 * LINE_HEIGHT_RATIO_140).sp,
    ),
    button02Altform = AltformStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = (12 * LINE_HEIGHT_RATIO_140).sp,
    ),
    button01Suit = SuitStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = (14 * LINE_HEIGHT_RATIO_140).sp,
    ),
    button02Suit = SuitStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = (16 * LINE_HEIGHT_RATIO_140).sp,
    )
)

@Immutable
data class KeeplyTypography(
    val header01: TextStyle,
    val header02: TextStyle,
    val header03: TextStyle,
    val header04: TextStyle,
    val header05: TextStyle,
    val header06: TextStyle,
    
    val subtitle01: TextStyle,
    val subtitle02: TextStyle,
    val subtitle03: TextStyle,
    
    val body: TextStyle,
    val caption01: TextStyle,
    val caption02: TextStyle,
    
    val button01Altform: TextStyle,
    val button02Altform: TextStyle,
    val button01Suit: TextStyle,
    val button02Suit: TextStyle
)

val LocalTypography = staticCompositionLocalOf {
    KeeplyTypography(
        header01 = AltformStyle,
        header02 = AltformStyle,
        header03 = AltformStyle,
        header04 = AltformStyle,
        header05 = AltformStyle,
        header06 = AltformStyle,
        subtitle01 = SuitStyle,
        subtitle02 = SuitStyle,
        subtitle03 = SuitStyle,
        body = SuitStyle,
        caption01 = SuitStyle,
        caption02 = SuitStyle,
        button01Altform = AltformStyle,
        button02Altform = AltformStyle,
        button01Suit = SuitStyle,
        button02Suit = SuitStyle
    )
}

private const val LINE_HEIGHT_RATIO_100 = 1.0    // 100%
private const val LINE_HEIGHT_RATIO_130 = 1.3    // 130%
private const val LINE_HEIGHT_RATIO_140 = 1.4    // 140%
