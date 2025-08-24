package com.keeply.presentation.ui.scan.crop

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun CropRoute(
    viewModel: CropViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()
    
    CropScreen(
        uri = uiState.uri.toUri()
    )
}

@Composable
fun CropScreen(
    uri: Uri? = null
) {
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

            val imageHeightPx = remember { mutableIntStateOf(0) }

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .padding(
                        horizontal = 34.dp,
                        vertical = 70.dp
                    )
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            
        }

        CropBar()
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
