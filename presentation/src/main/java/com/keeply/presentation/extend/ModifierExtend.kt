package com.keeply.presentation.extend

import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.shadow01(
    elevation: Dp = 8.dp,
    spotColor: Color = Color(0x1A000000),
    ambientColor: Color = Color(0x1A000000),
) = this.shadow(
    elevation = elevation,
    spotColor = spotColor,
    ambientColor = ambientColor,
)

fun Modifier.shadow03(
    elevation: Dp = 4.dp,
    spotColor: Color = Color(0x1F000000),
    ambientColor: Color = Color(0x1F000000),
) = this.shadow(
    elevation = elevation,
    spotColor = spotColor,
    ambientColor = ambientColor,
)
