package com.keeply.presentation.core.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val orange1000 = Color(0xFF591800)
val orange900 = Color(0xFF731F00)
val orange800 = Color(0xFF992900)
val orange700 = Color(0xFFBF3300)
val orange600 = Color(0xFFCC3600)
val orange500 = Color(0xFFE63D00)
val orange400 = Color(0xFFFF4400)
val orange300 = Color(0xFFFF9A76)
val orange200 = Color(0xFFFFE3D9)
val orange100 = Color(0xFFFFECE6)

val neutralBlack = Color(0xFF000000)
val neutral1000 = Color(0xFF1E1E1E)
val neutral900 = Color(0xFF323235)
val neutral800 = Color(0xFF545456)
val neutral700 = Color(0xFF6C6C6F)
val neutral600 = Color(0xFF828285)
val neutral500 = Color(0xFF9B9B9E)
val neutral400 = Color(0xFFBDBDBF)
val neutral300 = Color(0xFFD8D8D8)
val neutral200 = Color(0xFFEAEAEA)
val neutral100 = Color(0xFFF5F5F5)
val neutralWhite = Color(0xFFFFFFFF)

val folderYellow = Color(0xFFFFC453)
val folderBeige = Color(0xFFD4BCA1)
val folderBlue = Color(0xFF7AB9F2)
val folderPurple = Color(0xFFBBA6F4)
val folderGrey = Color(0xFFBDBDBF)

val error = Color(0xFFF22024)
val success = Color(0xFF1AA343)
val warning = Color(0xFFFFC721)

@Immutable
data class KeeplyColors(
    val orange1000: Color,
    val orange900: Color,
    val orange800: Color,
    val orange700: Color,
    val orange600: Color,
    val orange500: Color,
    val orange400: Color,
    val orange300: Color,
    val orange200: Color,
    val orange100: Color,

    val neutralBlack: Color,
    val neutral1000: Color,
    val neutral900: Color,
    val neutral800: Color,
    val neutral700: Color,
    val neutral600: Color,
    val neutral500: Color,
    val neutral400: Color,
    val neutral300: Color,
    val neutral200: Color,
    val neutral100: Color,
    val neutralWhite: Color,

    val folderYellow: Color,
    val folderBeige: Color,
    val folderBlue: Color,
    val folderPurple: Color,
    val folderGrey: Color,

    val error: Color,
    val success: Color,
    val warning: Color,
)

val LocalColors = staticCompositionLocalOf {
    KeeplyColors(
        orange1000 = orange1000,
        orange900 = orange900,
        orange800 = orange800,
        orange700 = orange700,
        orange600 = orange600,
        orange500 = orange500,
        orange400 = orange400,
        orange300 = orange300,
        orange200 = orange200,
        orange100 = orange100,
        neutralBlack = neutralBlack,
        neutral1000 = neutral1000,
        neutral900 = neutral900,
        neutral800 = neutral800,
        neutral700 = neutral700,
        neutral600 = neutral600,
        neutral500 = neutral500,
        neutral400 = neutral400,
        neutral300 = neutral300,
        neutral200 = neutral200,
        neutral100 = neutral100,
        neutralWhite = neutralWhite,
        folderYellow = folderYellow,
        folderBeige = folderBeige,
        folderBlue = folderBlue,
        folderPurple = folderPurple,
        folderGrey = folderGrey,
        error = error,
        success = success,
        warning = warning,
    )
}