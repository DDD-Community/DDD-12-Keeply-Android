package com.keeply.presentation.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.keeply.presentation.R
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun FolderIcon(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    tint: Color,
) {
    Box(
        modifier = modifier
    ) {
        Icon(
            modifier = iconModifier,
            painter = painterResource(R.drawable.ic_folder_file_tintable),
            contentDescription = "folder",
            tint = tint
        )
        Icon(
            modifier = iconModifier,
            painter = painterResource(R.drawable.ic_folder_file_stripe),
            contentDescription = null,
            tint = KeeplyTheme.colors.neutral100
        )
    }
}