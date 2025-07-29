package com.keeply.presentation.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.keeply.presentation.R
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
    Box(
        modifier = modifier
    ) {
        if(images.getOrNull(2) != null || images.isEmpty()) {
            ImageFrame(
                modifier = Modifier
                    .width(cardWidth)
                    .offset(x = 68.dp),
                painter = if (images.isEmpty()) painterResource(R.drawable.img_onboarding_03)
                        else images[2],
                rotate = -1f
            )
        }

        if(images.getOrNull(1) != null || images.isEmpty()) {
            ImageFrame(
                modifier = Modifier
                    .width(cardWidth)
                    .offset(x = 28.dp),
                painter = if (images.isEmpty()) painterResource(R.drawable.img_onboarding_02)
                        else images[1],
                rotate = 5f
            )
        }

        if(images.getOrNull(0) != null || images.isEmpty()) {
            ImageFrame(
                modifier = Modifier
                    .width(cardWidth),
                painter = if (images.isEmpty()) painterResource(R.drawable.img_onboarding_01)
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
