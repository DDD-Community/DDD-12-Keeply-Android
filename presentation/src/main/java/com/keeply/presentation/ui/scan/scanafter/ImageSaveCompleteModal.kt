package com.keeply.presentation.ui.scan.scanafter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.keeply.presentation.R
import com.keeply.presentation.core.components.BaseAlertModal
import com.keeply.presentation.core.components.KeeplyButton
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun ImageSaveCompleteModal(
    onNavigateToFolder: () -> Unit = {}
) {
    // 외부 클릭 다 무시 !!
    // TODO: 사이즈나 여백 내 맘대로 ..
    BaseAlertModal(
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_save_complete))
            val progress by animateLottieCompositionAsState(
                composition = composition,
                iterations = LottieConstants.IterateForever
            )

            LottieAnimation(
                modifier = Modifier
                    .height(200.dp), // todo
                composition = composition,
                progress = { progress }
            )

            KeeplyText(
                modifier = Modifier
                    .width(200.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.scan_complete_modal_title),
                style = KeeplyTheme.typography.subtitle01,
                textAlign = TextAlign.Center
            )

            KeeplyText(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .width(200.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.scan_complete_modal_content),
                style = KeeplyTheme.typography.body,
                color = KeeplyTheme.colors.neutral800,
                textAlign = TextAlign.Center
            )

            KeeplyButton(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth()
                    .height(54.dp),
                text = stringResource(R.string.scan_complete_modal_button),
                textStyle = KeeplyTheme.typography.button01Suit,
                onClick = {
                    onNavigateToFolder()
                }
            )
        }
    }
}

@Preview
@Composable
fun ImageSaveCompleteModalPreview() {
    KeeplyTheme {
        ImageSaveCompleteModal()
    }
}