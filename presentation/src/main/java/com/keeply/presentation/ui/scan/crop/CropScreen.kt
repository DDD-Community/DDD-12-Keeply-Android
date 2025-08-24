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

            if (actualImageSize != Size.Zero) {
                LaunchedEffect(actualImageSize, isInitialized) {
                    if (!isInitialized && actualImageSize != Size.Zero) {
                        val margin = 0.1f
                        val initialCropRect = Rect(
                            left = actualImageSize.width * margin,
                            top = actualImageSize.height * margin,
                            right = actualImageSize.width * (1f - margin),
                            bottom = actualImageSize.height * (1f - margin)
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
