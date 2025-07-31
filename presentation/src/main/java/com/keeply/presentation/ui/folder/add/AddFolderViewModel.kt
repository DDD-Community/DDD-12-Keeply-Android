package com.keeply.presentation.ui.folder.add

import androidx.lifecycle.ViewModel
import com.keeply.presentation.core.components.colorBar.FolderColor
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AddFolderViewModel @Inject constructor(): ViewModel(), ContainerHost<AddFolderState, AddFolderSideEffect> {
    override val container: Container<AddFolderState, AddFolderSideEffect> =
        container(AddFolderState())
    
    fun updateFolderName(name: String) = intent {
        reduce { state.copy(folderName = name) }
    }
    
    fun selectColor(color: FolderColor) = intent {
        reduce { state.copy(folderColor = color) }
    }
    
    fun createFolder() = intent {
        postSideEffect(AddFolderSideEffect.ShowCreateSuccess)
        postSideEffect(AddFolderSideEffect.NavigateBack)
    }
}