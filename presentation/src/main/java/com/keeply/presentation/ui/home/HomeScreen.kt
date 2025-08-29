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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.keeply.domain.extend.default
import com.keeply.domain.model.Screenshot
import com.keeply.presentation.R
import com.keeply.presentation.core.components.ImageFrame
import com.keeply.presentation.core.components.KeeplyAppBar
import com.keeply.presentation.core.components.KeeplyIconButton
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.extend.formatDate
import com.keeply.presentation.ui.home.component.HomeKeeplyFolderItem
import com.keeply.presentation.ui.home.component.HomeKeeplyScreenshotItem
import com.keeply.presentation.ui.home.component.HomeUncategorizedCard
import com.keeply.presentation.ui.home.component.KeeplyProgressBar
import com.keeply.presentation.util.isPermissionGranted
import com.keeply.presentation.util.openAppSettings
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.flowOf
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    onClickUncategorized: () -> Unit = {},
    onClickFolder: () -> Unit = {},
    onClickScreenshot: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val uiState by viewModel.collectAsState()
    val lazyPagingItems = viewModel.screenshots.collectAsLazyPagingItems()

    viewModel.setRestrictService(isPermissionGranted(context))

    LaunchedEffect(Unit) {
        viewModel.loadHomeData()
    }


    HomeScreen(
        state = uiState,
        lazyPagingItems = lazyPagingItems,
        isRestricted = uiState.isRestrictedService,
        latestScreenshotCount = viewModel.latestScreenshotCount,
        onClickSetting = { openAppSettings(context) },
        onClickUncategorized = onClickUncategorized,
        onClickFolder = onClickFolder,
        onClickScreenshot = onClickScreenshot
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    lazyPagingItems: LazyPagingItems<Screenshot>,
    isRestricted: Boolean = false,
    latestScreenshotCount: Int = 10,
    onClickSetting: () -> Unit = {},
    onClickUncategorized: () -> Unit = {},
    onClickFolder: () -> Unit = {},
    onClickScreenshot: (String) -> Unit = {}
) {
    state.homeData?.let { homeData ->
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
                    Image(
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
                if (isRestricted) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 11.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(KeeplyTheme.colors.neutral200)
                            .clickable { onClickSetting() }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            KeeplyText(
                                text = "권한 부족으로 서비스 내 일부 기능이 제한됩니다.\n설정에서 권한을 켜주세요.",
                                style = KeeplyTheme.typography.caption02,
                                color = KeeplyTheme.colors.neutral800
                            )

                            KeeplyText(
                                text = "권한 설정",
                                style = KeeplyTheme.typography.button01Suit.copy(
                                    textDecoration = TextDecoration.Underline
                                ),
                                color = KeeplyTheme.colors.neutral900
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    KeeplyProgressBar(
                        currentLength = homeData.imageCount
                    )

                    Row(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        HomeUncategorizedCard(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onClickUncategorized() },
                            title = "미분류",
                            count = homeData.uncategorizedImageCount,
                            images = homeData.uncategorizedImageList.map {
                                rememberAsyncImagePainter(it.presignedUrl)
                            }.toPersistentList()
                        )

                        HomeUncategorizedCard(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onClickUncategorized() },
                            title = "만료예정",
                            count = homeData.scheduledToDeleteImageCount,
                            images = homeData.scheduledToDeleteImageList.map {
                                rememberAsyncImagePainter(it.presignedUrl)
                            }.toPersistentList()
                        )
                    }
                }

                if (isRestricted.not()) {
                    KeeplyText(
                        modifier = Modifier
                            .padding(top = 36.dp, start = 16.dp),
                        text = "최근 스크린샷",
                        style = KeeplyTheme.typography.subtitle01,
                    )

                    // 최대 10개만 가져오기
                    val itemsToShow = (0 until minOf(lazyPagingItems.itemCount, latestScreenshotCount)).mapNotNull { index ->
                        lazyPagingItems[index]
                    }

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item { Spacer(modifier = Modifier.size(4.dp)) }

                        items(itemsToShow.size) { index ->
                            val screenshot = itemsToShow[index]
                            ImageFrame(
                                painter = rememberAsyncImagePainter(screenshot.uri),
                                modifier = Modifier
                                    .width(96.dp)
                                    .clickable { onClickScreenshot(screenshot.uri.toString()) }
                            )
                        }

                        item { Spacer(modifier = Modifier.size(4.dp)) }
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(top = 36.dp, start = 16.dp, end = 16.dp)
                ) {
                    if (homeData.recentFolders.isNotEmpty()) {
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

                            KeeplyIconButton(
                                painter = KeeplyTheme.icons.arrowRight,
                                onClick = { onClickFolder() }
                            )
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
                            repeat(homeData.recentFolders.size) { index ->
                                val homeFolder = homeData.recentFolders[index]
                                HomeKeeplyFolderItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            vertical = 18.dp
                                        ),
                                    homeFolder = homeFolder
                                )

                                if (index < homeData.recentFolders.size - 1) {
                                    HorizontalDivider(
                                        thickness = 1.dp,
                                        color = KeeplyTheme.colors.neutral200
                                    )
                                }
                            }
                        }
                    }

                    if (homeData.recentSavedImages.isNotEmpty()) {
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
                            repeat(homeData.recentSavedImages.size) { index ->
                                val homeImage = homeData.recentSavedImages[index]

                                HomeKeeplyScreenshotItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp),
                                    painter = rememberAsyncImagePainter(homeImage.presignedUrl),
                                    tag = homeImage.tag.default(),
                                    tagColor = homeImage.tagColor,
                                    date = homeImage.updatedAt.formatDate(),
                                    insight = homeImage.insight ?: "",
                                )

                                if (index < homeData.recentSavedImages.size - 1) {
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
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    KeeplyTheme {
        HomeScreen(
            state = HomeState() ,
            lazyPagingItems = flowOf(PagingData.empty<Screenshot>()).collectAsLazyPagingItems())
    }
}
