package com.keeply.presentation.ui.folder

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class FolderViewModel @Inject constructor(

): ContainerHost<FolderState, FolderSideEffect>, ViewModel() {
    override val container: Container<FolderState, FolderSideEffect> = container(FolderState())

    fun onClickTab(index: Int) = intent {
        reduce {
            state.copy(
                selectedTab = FolderTabs.fromIndex(index)
            )
        }
    }
    
    fun refreshFolders() = intent {
        // TODO: 폴더 목록을 다시 불러오는 로직 구현
        // 예: getFoldersUseCase 호출
    }
}