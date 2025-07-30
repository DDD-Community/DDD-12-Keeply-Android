package com.keeply.presentation.ui.scan.scanbefore

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.keeply.presentation.R
import com.keeply.presentation.core.components.BaseAlertModal
import com.keeply.presentation.core.components.GifImage
import com.keeply.presentation.core.components.KeeplyAppBar
import com.keeply.presentation.core.components.KeeplyButton
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.components.ScanBar
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.core.theme.neutral900
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ScanBeforeRoute(
    onBack: () -> Unit,
    viewModel: ScanBeforeViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()
    val isShowOnBoarding by viewModel.showOnBoardingModal.collectAsState()

    ScanBeforeScreen(
        uri = uiState.uri.toUri(),
        isShowOnBoarding = isShowOnBoarding,
        onBack = onBack,
        onNavigateToDetail = {},
        doNotRepeatButtonCallback = { viewModel.onDoNotShowAgain() }
    )
}

@Composable
fun ScanBeforeScreen(
    uri: Uri? = null,
    isShowOnBoarding: Boolean = false,
    onBack: () -> Unit,
    onNavigateToDetail: () -> Unit,
    doNotRepeatButtonCallback: () -> Unit
) {
    var isMenuVisible by remember { mutableStateOf(true) }
    var isShowDialog by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(neutral900)
    ) {

        if (isShowOnBoarding && isShowDialog) {
            ScanOnBoardingModal(
                confirmButtonCallback = { isShowDialog = !isShowDialog },
                doNotRepeatButtonCallback = { doNotRepeatButtonCallback() }
            )
        }

        val painter = if (LocalInspectionMode.current || uri == null) {
            painterResource(id = R.drawable.img_onboarding_02)
        } else {
            rememberAsyncImagePainter(uri)
        }

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .clickable { isMenuVisible = !isMenuVisible },
            contentScale = ContentScale.Fit
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
                    .align(Alignment.BottomCenter),
            ) {

                ScanBar(
                    modifier = Modifier
                        .align(Alignment.Center),
                    onClickCrop = { },
                    onClickScan = { }
                )

                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(KeeplyTheme.colors.neutral200)
                        .padding(11.dp)
                        .align(Alignment.CenterEnd)
                        .clickable { },
                    painter = KeeplyTheme.icons.skip,
                    tint = KeeplyTheme.colors.neutral800,
                    contentDescription = "crop",
                )
            }
        }
    }
}

@Composable
fun ScanOnBoardingModal(
    confirmButtonCallback: () -> Unit,
    doNotRepeatButtonCallback: () -> Unit
) {
    BaseAlertModal(
        onDismissCallback = confirmButtonCallback,
        underContent = {
            KeeplyText(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 12.dp)
                    .clickable { doNotRepeatButtonCallback() },
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append("다시 보지 않기")
                    }
                },
                style = KeeplyTheme.typography.caption02,
                color = KeeplyTheme.colors.neutralWhite
            )
        }
    ) {
        Column(
            modifier = Modifier
                .width(283.dp)
                .padding(top = 32.dp, start = 12.dp, end = 12.dp, bottom = 12.dp),
        ) {
            GifImage(
                modifier = Modifier
                    .width(163.dp)
                    .padding(bottom = 10.dp)
                    .align(Alignment.CenterHorizontally),
                videoId = R.drawable.vid_scan_onboarding
            )

            KeeplyText(
                modifier = Modifier
                    .width(200.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.scan_onboarding_title),
                style = KeeplyTheme.typography.header02,
                textAlign = TextAlign.Center
            )

            KeeplyText(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .width(200.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.scan_onboarding_content),
                style = KeeplyTheme.typography.body,
                color = KeeplyTheme.colors.neutral800,
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                KeeplyButton(
                    modifier = Modifier
                        .weight(1f),
                    text = stringResource(R.string.scan_onboarding_confirm),
                    onClick = confirmButtonCallback
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScanScreenPreview() {
    KeeplyTheme {
        ScanBeforeScreen(
            onBack = { },
            onNavigateToDetail = { },
            doNotRepeatButtonCallback = { }
        )
    }
}