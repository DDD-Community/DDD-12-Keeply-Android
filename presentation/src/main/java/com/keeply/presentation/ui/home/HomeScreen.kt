package com.keeply.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.keeply.presentation.R
import com.keeply.presentation.core.components.ImageFrame
import com.keeply.presentation.core.components.KeeplyAppBar
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.ui.home.component.HomeKeeplyFolderItem
import com.keeply.presentation.ui.home.component.HomeKeeplyScreenshotItem
import com.keeply.presentation.ui.home.component.HomeUncategorizedCard
import com.keeply.presentation.ui.home.component.KeeplyProgressBar
import kotlinx.collections.immutable.persistentListOf
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()

    HomeScreen()
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(KeeplyTheme.colors.neutral100)
    ) {
        KeeplyAppBar(
            customTitleContent = {
                Image(
                    modifier = Modifier
                        .width(72.dp)
                        .align(Alignment.CenterStart),
                    painter = painterResource(R.drawable.ic_keeply_text_logo),
                    contentDescription = "Keeply"
                )
            },
            leadingIcon = null,
            trailingIcon = {
                Icon(
                    painter = KeeplyTheme.icons.alarmStateOn,
                    contentDescription = "Notification"
                )
            },
            onClickTrailing = {}
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .padding(
                    top = 20.dp,
                    bottom = 112.dp
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                KeeplyProgressBar(
                    currentLength = 60
                )

                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HomeUncategorizedCard(
                        modifier = Modifier
                            .weight(1f),
                        title = "미분류",
                        count = 7,
                        images = persistentListOf(
                            painterResource(R.drawable.img_onboarding_01),
                            painterResource(R.drawable.img_onboarding_02),
                            painterResource(R.drawable.img_onboarding_03),
                        )
                    )

                    HomeUncategorizedCard(
                        modifier = Modifier
                            .weight(1f),
                        title = "만료예정",
                        count = 0
                    )
                }

                KeeplyText(
                    modifier = Modifier
                        .padding(top = 36.dp),
                    text = "최근 스크린샷",
                    style = KeeplyTheme.typography.subtitle01,
                )
            }

            LazyRow(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 38.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item { Spacer(modifier = Modifier.size(4.dp)) }
                items(5) {
                    ImageFrame(
                        modifier = Modifier
                            .width(96.dp),
                        painter = ColorPainter(KeeplyTheme.colors.neutral200)
                    )
                }
                item { Spacer(modifier = Modifier.size(4.dp)) }
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    KeeplyText(
                        modifier = Modifier
                            .weight(1f),
                        text = "최근 업데이트된 폴더",
                        style = KeeplyTheme.typography.subtitle01,
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(2.dp))
                            .clickable {

                            }
                            .background(KeeplyTheme.colors.neutralBlack)
                            .padding(
                                vertical = 4.dp,
                                horizontal = 12.dp
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(18.dp),
                            painter = KeeplyTheme.icons.arrowRight,
                            contentDescription = "최근 업데이트된 폴더",
                            tint = KeeplyTheme.colors.neutralWhite
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(KeeplyTheme.colors.neutralWhite)
                        .padding(
                            vertical = 2.dp,
                            horizontal = 16.dp
                        )
                ) {
                    val count = 4

                    repeat(count) { index ->
                        HomeKeeplyFolderItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = 18.dp
                                )
                        )

                        if (index < count - 1) {
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = KeeplyTheme.colors.neutral200
                            )
                        }
                    }
                }

                KeeplyText(
                    modifier = Modifier
                        .padding(top = 36.dp),
                    text = "최근 저장한 스크린샷",
                    style = KeeplyTheme.typography.subtitle01,
                )

                Column(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(KeeplyTheme.colors.neutralWhite)
                        .padding(horizontal = 16.dp)
                ) {
                    val count = 4

                    repeat(count) { index ->
                        HomeKeeplyScreenshotItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        )

                        if (index < count - 1) {
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = KeeplyTheme.colors.neutral200
                            )
                        }
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    KeeplyTheme {
        HomeScreen()
    }
}
