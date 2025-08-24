package com.keeply.presentation.ui.scan.scanbefore

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
import com.keeply.domain.model.ScanAnalyze
import com.keeply.presentation.R
import com.keeply.presentation.core.components.BaseAlertModal
import com.keeply.presentation.core.components.GifImage
import com.keeply.presentation.core.components.KeeplyAppBar
import com.keeply.presentation.core.components.KeeplyButton
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.components.ScanBar
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.core.theme.neutral900
import com.keeply.presentation.util.toFile
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ScanBeforeRoute(
    onBack: () -> Unit,
    onNavigateToCrop: (Uri) -> Unit,
    onNavigateToScanAfter: (Uri, ScanAnalyze, Boolean) -> Unit,
    viewModel: ScanBeforeViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val uiState by viewModel.collectAsState()

    ScanBeforeScreen(
        uri = uiState.uri.toUri(),
        isShowOnBoarding = uiState.isShowOnBoarding,
        isScanCompleted = uiState.isScanCompleted,
        onBack = onBack,
        onNavigateToCrop = { onNavigateToCrop(uiState.uri.toUri()) },
        onBoardingConfirmCallback = { viewModel.onBoardingConfirmClicked() },
        onBoardingDisabledCallback = { viewModel.onBoardingNeverShowClicked() },
        callScan = { imageUri, isScanSkip ->
            // TODO real -> Crop후 imageUri 사용하도록 수정하는게 좋을 듯함
            viewModel.scanImage(
                image = uiState.uri.toUri().toFile(context),
                successCallback = { result ->
                    onNavigateToScanAfter(uiState.uri.toUri(), result, false)
                }
            )
        }
    )
}

@Composable
fun ScanBeforeScreen(
    uri: Uri? = null,
    isShowOnBoarding: Boolean = false,
    isScanCompleted: Boolean = false,
    onBack: () -> Unit,
    onNavigateToCrop: () -> Unit,
    onBoardingConfirmCallback: () -> Unit,
    onBoardingDisabledCallback: () -> Unit,
    callScan: (Uri, Boolean) -> Unit = { _, _ -> }
) {
    if (uri == null) return // 모달을 띄우거나 ~

    var isMenuVisible by remember { mutableStateOf(true) }
    var isScanLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(neutral900)
    ) {

        if (isShowOnBoarding) {
            ScanOnBoardingModal(
                onBoardingConfirmCallback = onBoardingConfirmCallback,
                onBoardingDisabledCallback = onBoardingDisabledCallback
            )
        }

        val interactionSource = remember { MutableInteractionSource() }
        val clickableModifier = if (isScanLoading) {
            Modifier
                .padding(35.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { }
                )
        } else {
            Modifier
                .padding(0.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = LocalIndication.current,
                    onClick = { isMenuVisible = !isMenuVisible }
                )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Center)
                .then(clickableModifier)
        ) {
            val painter = if (LocalInspectionMode.current) {
                painterResource(id = R.drawable.img_onboarding_02)
            } else {
                rememberAsyncImagePainter(uri)
            }

            val imageHeightPx = remember { mutableIntStateOf(0) }
            val imageHeightDp = with(LocalDensity.current) { imageHeightPx.intValue.toDp() }
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        imageHeightPx.intValue = coordinates.size.height // px 단위
                    },
                contentScale = ContentScale.Fit
            )

            if (isScanLoading && isScanCompleted.not()) {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_scan))
                val progress by animateLottieCompositionAsState(
                    composition = composition,
                    iterations = LottieConstants.IterateForever
                )
                LottieAnimation(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(imageHeightDp),
                    contentScale = ContentScale.FillBounds,
                    composition = composition,
                    progress = { progress }
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
                        onClickCrop = onNavigateToCrop,
                        onClickScan = {
                            isScanLoading = true
                            callScan(uri, false)
                        }
                    )

                    Icon(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(KeeplyTheme.colors.neutral200)
                            .clickable { callScan(uri, true) }
                            .padding(11.dp)
                            .align(Alignment.CenterEnd),
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
    onBoardingConfirmCallback: () -> Unit,
    onBoardingDisabledCallback: () -> Unit
) {
    BaseAlertModal(
        onDismissCallback = onBoardingConfirmCallback,
        underContent = {
            KeeplyText(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 12.dp)
                    .clickable { onBoardingDisabledCallback() },
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
                    onClick = onBoardingConfirmCallback
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
            onBoardingConfirmCallback = { },
            onBoardingDisabledCallback = { }
        )
    }
}