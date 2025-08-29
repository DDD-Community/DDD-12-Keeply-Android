package com.keeply.presentation.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.core.theme.neutral800
import com.keeply.presentation.extend.shadow01

@Composable
fun OriginalSizeImageFrame(
    modifier: Modifier = Modifier,
    imageUrl: String,
) {
    Card(
        modifier = modifier
            .wrapContentSize()
            .shadow01(),
        shape = RoundedCornerShape(2.dp),
        border = BorderStroke(6.dp, KeeplyTheme.colors.neutralWhite),
        colors = CardDefaults.cardColors(
            containerColor = KeeplyTheme.colors.neutralWhite
        )
    ) {
        Box(
            modifier = Modifier
                .padding(2.dp)
                .clip(RoundedCornerShape(2.dp))
        ) {
            SubcomposeAsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            ) {
                val state = painter.state
                if (state is AsyncImagePainter.State.Success) {
                    val ratio = state.painter.intrinsicSize.width / state.painter.intrinsicSize.height
                    SubcomposeAsyncImageContent(
                        modifier = Modifier.aspectRatio(ratio)
                    )
                } else {
                    // 로딩 상태 placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16 / 9f) // 기본 비율
                            .background(KeeplyTheme.colors.neutral200)
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun FlexibleImageFramePreview() {
    KeeplyTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(neutral800)
                .padding(20.dp)
        ) {
            OriginalSizeImageFrame(
                imageUrl = ""
            )
        }
    }
}
