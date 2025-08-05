package com.keeply.presentation.ui.folder.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.keeply.presentation.R
import com.keeply.presentation.core.components.KeeplyAppBar
import com.keeply.presentation.core.components.KeeplyModalBottomSheet
import com.keeply.presentation.core.components.KeeplyButton
import com.keeply.presentation.core.components.KeeplyButtonSize
import com.keeply.presentation.core.components.KeeplyIconButton
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.components.colorBar.FolderColor
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.ui.folder.detail.component.FolderModifyBottomSheetContent
import com.keeply.presentation.ui.home.component.HomeKeeplyScreenshotItem
import kotlinx.collections.immutable.persistentListOf
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun FolderDetailRoute(
    onBack: () -> Unit,
    viewModel: FolderDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()
    
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is FolderDetailSideEffect.ShowError -> {

            }
        }
    }
    
    FolderDetailScreen(
        state = uiState,
        onBack = onBack,
        onHideBottomSheet = viewModel::hideBottomSheet,
        onShowBottomSheet = viewModel::showBottomSheet,
        onFolderNameChange = viewModel::updateFolderName,
        onColorSelect = viewModel::selectColor,
        onDeleteClick = { /* TODO: Implement delete */ },
        onSaveClick = { /* TODO: Implement save */ }
    )
}

@Composable
fun FolderDetailScreen(
    state: FolderDetailState,
    onBack: () -> Unit,
    onHideBottomSheet: () -> Unit = {},
    onShowBottomSheet: () -> Unit = {},
    onFolderNameChange: (String) -> Unit = {},
    onColorSelect: (FolderColor) -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onSaveClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(KeeplyTheme.colors.neutral100)
    ) {
        KeeplyAppBar(
            title = state.folderName,
            leadingIcon = {
                Icon(
                    painter = KeeplyTheme.icons.chevronLeft,
                    contentDescription = "Back",
                    tint = KeeplyTheme.colors.neutralBlack
                )
            },
            trailingIcon = {
                Icon(
                    painter = KeeplyTheme.icons.menuVertical,
                    contentDescription = "menu",
                    tint = KeeplyTheme.colors.neutralBlack
                )
            },
            onClickLeading = onBack,
            onClickTrailing = onShowBottomSheet
        )

        val isNotEmpty = state.images.isNotEmpty()

        if(isNotEmpty) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 12.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    KeeplyText(
                        modifier = Modifier
                            .weight(1f),
                        text = "${state.images.size}개",
                        style = KeeplyTheme.typography.subtitle02,
                        color = KeeplyTheme.colors.neutral600
                    )

                    KeeplyIconButton(
                        painter = KeeplyTheme.icons.add,
                        onClick = {  }
                    )
                }

                LazyColumn(
                    contentPadding = PaddingValues(
                        bottom = 112.dp
                    )
                ) {
                    items(state.images.size) { index ->
                        val image = state.images[index]
                        HomeKeeplyScreenshotItem(
                            modifier = Modifier
                                .padding(
                                    vertical = 16.dp
                                ),
                            painter = rememberAsyncImagePainter(model = image.presignedUrl),
                            tag = image.tag,
                            insight = image.insight,
                        )

                        if (index < state.images.size - 1 ) {
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = KeeplyTheme.colors.neutral200
                            )
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_add_screenshot))
                    val progress by animateLottieCompositionAsState(
                        composition = composition,
                        iterations = LottieConstants.IterateForever
                    )

                    LottieAnimation(
                        composition = composition,
                        progress = { progress }
                    )

                    KeeplyText(
                        modifier = Modifier
                            .padding(top = 12.dp),
                        text = "폴더가 비어있어요.\n스크린샷을 추가해주세요.",
                        style = KeeplyTheme.typography.button01Suit,
                        color = KeeplyTheme.colors.neutral500
                    )

                    KeeplyButton(
                        modifier = Modifier
                            .padding(top = 28.dp)
                            .width(198.dp),
                        text = "스크린샷 추가",
                        icon = KeeplyTheme.icons.add,
                        buttonSize = KeeplyButtonSize.SMALL,
                        onClick = {}
                    )
                }
            }
        }
    }

    if (state.isShowBottomSheet) {
        KeeplyModalBottomSheet(
            onDismissRequest = onHideBottomSheet
        ) {
            FolderModifyBottomSheetContent(
                folderName = state.editingFolderName,
                selectedColor = state.selectedColor,
                onFolderNameChange = onFolderNameChange,
                onColorSelect = onColorSelect,
                onDeleteClick = onDeleteClick,
                onSaveClick = onSaveClick
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun FolderDetailScreenPreview() {
    KeeplyTheme {
        FolderDetailScreen(
            state = FolderDetailState(
                folderId = 1,
                folderName = "나의 폴더",
                images = persistentListOf()
            ),
            onBack = { }
        )
    }
}