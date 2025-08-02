package com.keeply.presentation.ui.folder.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class FolderDetailViewModel @Inject constructor(

): ViewModel(), ContainerHost<FolderDetailState, FolderDetailSideEffect> {
    override val container: Container<FolderDetailState, FolderDetailSideEffect> = container(FolderDetailState())



}