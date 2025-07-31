package com.keeply.presentation.ui.scan.scanbefore

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.keeply.presentation.R
import com.keeply.presentation.core.components.BaseAlertModal
import com.keeply.presentation.core.components.GifImage
import com.keeply.presentation.core.components.KeeplyAppBar
import com.keeply.presentation.core.components.KeeplyButton
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.components.ScanBar
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.core.theme.neutral900
import com.keeply.presentation.util.uriToBase64
import com.keeply.presentation.core.theme.orange400
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ScanBeforeRoute(
    onBack: () -> Unit,
    onNavigateToCrop: (Uri) -> Unit,
    viewModel: ScanBeforeViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val uiState by viewModel.collectAsState()
    val isShowOnBoarding by viewModel.showOnBoardingModal.collectAsState()

    ScanBeforeScreen(
        uri = uiState.uri.toUri(),
        isShowOnBoarding = isShowOnBoarding,
        onBack = onBack,
        onNavigateToCrop = onNavigateToCrop,
        doNotRepeatButtonCallback = { viewModel.onDoNotShowAgain() },
        callScan = {
            viewModel.scanImage(
                image = uriToBase64(
                    context = context,
                    uri = uiState.uri.toUri()
                ),
                successCallback = {

                }
            )
        }
    )
}

@Composable
fun ScanBeforeScreen(
    uri: Uri? = null,
    isShowOnBoarding: Boolean = false,
    onBack: () -> Unit,
    onNavigateToCrop: (Uri) -> Unit,
    doNotRepeatButtonCallback: () -> Unit,
    callScan: () -> Unit = {}
) {
    if (uri == null) return // 모달을 띄우거나 ~

    var isMenuVisible by remember { mutableStateOf(true) }
    var isShowDialog by remember { mutableStateOf(true) }
    var isScanLoading by remember { mutableStateOf(false) }

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

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Center)
                .padding(if (isScanLoading) 35.dp else 0.dp)
                .clickable { isMenuVisible = !isMenuVisible }
        ) {
            val painter = if (LocalInspectionMode.current) {
                painterResource(id = R.drawable.img_onboarding_02)
            } else {
                rememberAsyncImagePainter(uri)
            }

            val overlayStart = maxHeight / 5  // Scan 그라데이션 시작 위치

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Fit
            )

            if (isScanLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .offset(y = overlayStart)
                        .background(orange400)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(290.dp)
                        .offset(y = overlayStart)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    orange400.copy(alpha = 0.3f),
                                    Color.Transparent
                                )
                            )
                        )
                )
            }
        }

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

            if (isScanLoading) {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_loading))
                val progress by animateLottieCompositionAsState(
                    composition = composition,
                    iterations = LottieConstants.IterateForever
                )

                LottieAnimation(
                    modifier = Modifier
                        .width(67.dp)
                        .height(48.dp)
                        .align(Alignment.BottomCenter)
                        .offset(y = (-32.5).dp),
                    composition = composition,
                    progress = { progress }
                )
            } else {
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
                        onClickScan = {
                            isScanLoading = true
                            callScan()
                        }
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
            onNavigateToCrop = { },
            doNotRepeatButtonCallback = { }
        )
    }
}