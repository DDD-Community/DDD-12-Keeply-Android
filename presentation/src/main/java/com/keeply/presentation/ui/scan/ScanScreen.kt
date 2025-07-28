package com.keeply.presentation.ui.scan

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun ScanRoute() {
    ScanScreen()
}

@Composable
fun ScanScreen() {
    ScreenshotScreen()
}

@Composable
fun ScreenshotScreen(viewModel: LocalScreenshotViewModel = hiltViewModel()) {
    val lazyPagingItems = viewModel.screenshots.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize()
    ) {
        items(lazyPagingItems.itemCount) { index ->
            lazyPagingItems[index]?.let { screenshot ->
                Image(
                    painter = rememberAsyncImagePainter(screenshot.uri),
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(1.dp)
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScanScreenPreview() {
    KeeplyTheme {
        ScanScreen()
    }
}