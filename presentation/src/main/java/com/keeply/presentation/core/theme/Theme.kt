package com.keeply.presentation.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val DarkColorScheme = KeeplyColors(
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

private val LightColorScheme = KeeplyColors(
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

@Composable
fun KeeplyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val iconography = keeplyIconography()

    CompositionLocalProvider(
        LocalTypography provides Typography,
        LocalColors provides colorScheme,
        LocalIconography provides iconography
    ) {
        MaterialTheme(
            content = content
        )
    }
}

object KeeplyTheme {
    val typography: KeeplyTypography
        @Composable
        get() = LocalTypography.current

    val colors: KeeplyColors
        @Composable
        get() = LocalColors.current
}