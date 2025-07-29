package com.keeply.presentation.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.extend.shadow01
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ImageFrame(
    painter: Painter,
    modifier: Modifier = Modifier,
    rotate: Float = 0f,
    contentScale: ContentScale = ContentScale.FillWidth,
) {
    Card(
        modifier = modifier
            .rotate(rotate)
            .shadow01()
            .aspectRatio(0.75f),
        shape = RoundedCornerShape(2.dp),
        border = BorderStroke(2.dp, KeeplyTheme.colors.neutralWhite),
        colors = CardDefaults.cardColors(
            containerColor = KeeplyTheme.colors.neutralWhite
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
                .clip(RoundedCornerShape(2.dp))
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = "image",
                contentScale = contentScale
            )
        }
    }

}

@Composable
fun ImageFrameList(
    modifier: Modifier = Modifier,
    cardWidth: Dp = 92.dp,
    images: ImmutableList<Painter> = persistentListOf(),
) {
    val imageCount = when {
        images.isEmpty() -> 3 // 기본 이미지 사용시
        else -> images.size.coerceIn(0, 3) // 최대 3개까지만 표시
    }
    
    // cardWidth에 비례한 offset 계산 (비율 기반)
    val secondCardOffset = cardWidth * 0.304f // 28dp / 92dp ≈ 0.304
    val thirdCardOffset = cardWidth * 0.739f  // 68dp / 92dp ≈ 0.739
    
    // 이미지 개수에 따른 전체 너비 계산
    val totalWidth = when (imageCount) {
        1 -> cardWidth
        2 -> cardWidth + secondCardOffset
        3 -> cardWidth + thirdCardOffset
        else -> cardWidth
    }
    
    // 카드의 높이 (aspect ratio 0.75 -> height = width * 1.33)
    val cardHeight = cardWidth * 1.33f
    
    Box(
        modifier = modifier
            .size(width = totalWidth, height = cardHeight)
    ) {
        // 세 번째 카드 (맨 뒤)
        if(imageCount >= 3) {
            ImageFrame(
                modifier = Modifier
                    .width(cardWidth)
                    .offset(x = thirdCardOffset),
                painter = if (images.isEmpty()) ColorPainter(KeeplyTheme.colors.neutral300)
                        else images[2],
                rotate = -1f
            )
        }

        // 두 번째 카드 (중간)
        if(imageCount >= 2) {
            ImageFrame(
                modifier = Modifier
                    .width(cardWidth)
                    .offset(x = secondCardOffset),
                painter = if (images.isEmpty()) ColorPainter(KeeplyTheme.colors.neutral300)
                        else images[1],
                rotate = 5f
            )
        }

        // 첫 번째 카드 (맨 앞)
        if(imageCount >= 1) {
            ImageFrame(
                modifier = Modifier
                    .width(cardWidth),
                painter = if (images.isEmpty()) ColorPainter(KeeplyTheme.colors.neutral300)
                        else images[0],
                rotate = -2f
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageFramePreview() {
    KeeplyTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            ImageFrameList()
        }
    }
}
