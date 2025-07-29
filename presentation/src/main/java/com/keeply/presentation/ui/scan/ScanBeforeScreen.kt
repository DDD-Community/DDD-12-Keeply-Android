package com.keeply.presentation.ui.scan

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.keeply.presentation.core.components.KeeplyAppBar
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.core.theme.orange200

@Composable
fun ScanBeforeScreen(
    uri: Uri,
    onBack: () -> Unit,
    onNavigateToDetail: () -> Unit
) {
    var isMenuVisible by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { isMenuVisible = !isMenuVisible }
            .background(orange200) // TODO: 삭제해야됨 isMenuVisible 확인용
    ) {
        Image(
            painter = rememberAsyncImagePainter(uri),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .clickable { },
            contentScale = ContentScale.Crop
        )

        if (isMenuVisible) {
            KeeplyAppBar(
                modifier = Modifier
                    .align(Alignment.TopCenter),
                customTitleContent = {
                    KeeplyText(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .align(Alignment.CenterStart),
                        text = "Scan",
                        style = KeeplyTheme.typography.header03,
                        color = KeeplyTheme.colors.neutralWhite
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = KeeplyTheme.icons.chevronLeft,
                        contentDescription = "Back",
                        tint = KeeplyTheme.colors.neutralWhite
                    )
                },
                onClickLeading = onBack,
                backgroundColor = KeeplyTheme.colors.neutralBlack,
                contentColor = KeeplyTheme.colors.neutralWhite
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScanScreenPreview() {
    KeeplyTheme {
        ScanBeforeScreen(
            uri = "android.resource://com.your.package.name/drawable/ic_alarm_state_on".toUri(),
            onBack = { },
            onNavigateToDetail = { }
        )
    }
}