package com.keeply.presentation.ui.folder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keeply.domain.folder.usecase.GetFolderDetailUseCase
import com.keeply.domain.folder.usecase.GetFoldersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class FolderViewModel @Inject constructor(
    private val getFoldersUseCase: GetFoldersUseCase,
    private val getFolderDetailUseCase: GetFolderDetailUseCase
) : ContainerHost<FolderState, FolderSideEffect>, ViewModel() {
    override val container: Container<FolderState, FolderSideEffect> = container(FolderState())

    init {
        loadFolders()
    }

    fun onClickTab(index: Int) = intent {
        val newTab = FolderTabs.fromIndex(index)
        reduce {
            state.copy(
                selectedTab = newTab
            )
        }
        
        // 미분류 탭을 선택했을 때 uncategorized 폴더 조회
        if (newTab == FolderTabs.Uncategorized && state.uncategorizedImages.isEmpty()) {
            loadUncategorizedImages()
        }
    }

    fun refreshFolders() {
        loadFolders()
    }

    private fun loadFolders() = intent {
        viewModelScope.launch {
            getFoldersUseCase()
                .onStart {
                    reduce { state.copy(isLoading = true) }
                }
                .catch { e ->
                    reduce {
                        state.copy(
                            isLoading = false,
                            error = e.message
                        )
                    }
                }
                .collect { folders ->
                    reduce {
                        state.copy(
                            isLoading = false,
                            folders = folders.toPersistentList(),
                            error = null
                        )
                    }
                }
        }
    }
    
    private fun loadUncategorizedImages() = intent {
        viewModelScope.launch {
            getFolderDetailUseCase("uncategorized")
                .onStart {
                    reduce { state.copy(isLoading = true) }
                }
                .catch { e ->
                    reduce {
                        state.copy(
                            isLoading = false,
                            error = e.message
                        )
                    }
                }
                .collect { images ->
                    reduce {
                        state.copy(
                            isLoading = false,
                            uncategorizedImages = images.toPersistentList(),
                            error = null
                        )
                    }
                }
        }
    }
}