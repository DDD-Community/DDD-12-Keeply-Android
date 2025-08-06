package com.keeply.presentation.ui.folder.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FolderDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    val folderId: Long = savedStateHandle.get<Long>("folderId") ?: 0L
    val folderName: String = savedStateHandle.get<String>("folderName") ?: ""
}