package com.keeply.presentation.ui.folder.detail

import androidx.compose.runtime.Immutable

@Immutable
data class FolderDetailState(
    val title: String = ""
)

sealed interface FolderDetailSideEffect

