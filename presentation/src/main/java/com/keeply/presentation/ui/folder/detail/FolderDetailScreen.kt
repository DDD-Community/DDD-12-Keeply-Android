package com.keeply.presentation.ui.folder.detail

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun FolderDetailRoute(
    viewModel: FolderDetailViewModel = hiltViewModel()
) {
    val uiState = viewModel.collectAsState()
}

@Composable
fun FolderDetailScreen(

) {

}