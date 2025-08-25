package com.keeply.presentation.ui.scan.crop

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.keeply.presentation.core.theme.KeeplyTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.keeply.presentation.R
import com.keeply.presentation.core.components.CropBar
import com.keeply.presentation.core.components.KeeplyAppBar
import com.keeply.presentation.core.components.KeeplyText
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun CropRoute(
    onBack: () -> Unit = {},
    onCropCompleted: (String) -> Unit = {},
    viewModel: CropViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()
    
    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is CropSideEffect.CropCompleted -> {
                    onCropCompleted(sideEffect.croppedImageUri)
                }
            }
        }
    }
    
    CropScreen(
        uri = if (uiState.uri.isNotEmpty()) uiState.uri.toUri() else null,
        cropRect = uiState.cropRect,
        imageSize = uiState.imageSize,
        onBack = onBack,
        onCropRectChange = { newRect -> 
            viewModel.updateCropRect(newRect)
        },
        onImageSizeChanged = { size ->
            viewModel.updateImageSize(size)
        },
        onCropComplete = {
            viewModel.cropImage()
        }
    )
}

@Composable
fun CropScreen(
    uri: Uri? = null,
    cropRect: Rect = Rect.Zero,
    imageSize: Size = Size.Zero,
    onBack: () -> Unit = {},
    onCropRectChange: (Rect) -> Unit = {},
    onImageSizeChanged: (Size) -> Unit = {},
    onCropComplete: () -> Unit = {}
) {
    val density = LocalDensity.current
    var actualImageSize by remember { mutableStateOf(Size.Zero) }
    var localCropRect by remember { mutableStateOf(Rect.Zero) }
    var isInitialized by remember { mutableStateOf(false) }
    var intrinsicImageSize by remember { mutableStateOf(Size.Zero) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(KeeplyTheme.colors.neutral900)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            val painter = if (LocalInspectionMode.current) {
                painterResource(id = R.drawable.img_onboarding_02)
            } else {
                rememberAsyncImagePainter(uri)
            }

            // Painter의 intrinsic 크기 얻기
            LaunchedEffect(painter) {
                val intrinsicSize = painter.intrinsicSize
                if (intrinsicSize != Size.Unspecified && intrinsicSize.width > 0f && intrinsicSize.height > 0f) {
                    intrinsicImageSize = intrinsicSize
                }
            }

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .padding(
                        horizontal = 34.dp,
                        vertical = 70.dp
                    )
                    .fillMaxSize()
                    .onGloballyPositioned { coordinates ->
                        val newSize = Size(
                            width = coordinates.size.width.toFloat(),
                            height = coordinates.size.height.toFloat()
                        )
                        actualImageSize = newSize
                        onImageSizeChanged(newSize)
                    },
                contentScale = ContentScale.Fit
            )

            if (actualImageSize != Size.Zero && intrinsicImageSize != Size.Zero) {
                LaunchedEffect(actualImageSize, intrinsicImageSize, isInitialized) {
                    if (!isInitialized && actualImageSize != Size.Zero && intrinsicImageSize != Size.Zero) {
                        // ContentScale.Fit으로 실제 표시되는 이미지 크기 계산
                        val imageAspectRatio = intrinsicImageSize.width / intrinsicImageSize.height
                        val viewAspectRatio = actualImageSize.width / actualImageSize.height
                        
                        val (displayedWidth, displayedHeight) = if (imageAspectRatio > viewAspectRatio) {
                            // 이미지가 더 넓음 - width에 맞춰짐
                            actualImageSize.width to actualImageSize.width / imageAspectRatio
                        } else {
                            // 이미지가 더 높음 - height에 맞춰짐
                            actualImageSize.height * imageAspectRatio to actualImageSize.height
                        }
                        
                        // 중앙 정렬된 이미지의 실제 위치 계산
                        val offsetX = (actualImageSize.width - displayedWidth) / 2f
                        val offsetY = (actualImageSize.height - displayedHeight) / 2f
                        
                        // 실제 표시되는 이미지 영역에 맞춰 초기 크롭 영역 설정
                        val margin = 0.02f // 아주 작은 마진 (2%)
                        val initialCropRect = Rect(
                            left = offsetX + (displayedWidth * margin),
                            top = offsetY + (displayedHeight * margin),
                            right = offsetX + (displayedWidth * (1f - margin)),
                            bottom = offsetY + (displayedHeight * (1f - margin))
                        )
                        localCropRect = initialCropRect
                        onCropRectChange(initialCropRect)
                        isInitialized = true
                    }
                }
                
                CropOverlay(
                    modifier = Modifier
                        .padding(
                            horizontal = 34.dp,
                            vertical = 70.dp
                        )
                        .fillMaxSize(),
                    cropRect = localCropRect,
                    onCropRectChange = { newRect ->
                        localCropRect = newRect
                        onCropRectChange(newRect)
                    },
                    imageSize = actualImageSize
                )
            }
        }

        CropBar(
            onClickCancel = onBack,
            onCropClick = onCropComplete
        )
    }
}

@Preview
@Composable
fun CropScreenPreview() {
    KeeplyTheme {
        CropScreen(
            uri = null
        )
    }
}
