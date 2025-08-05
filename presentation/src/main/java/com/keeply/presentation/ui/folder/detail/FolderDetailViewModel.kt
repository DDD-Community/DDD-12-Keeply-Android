package com.keeply.presentation.ui.folder.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keeply.domain.folder.usecase.GetFolderDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class FolderDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFolderDetailUseCase: GetFolderDetailUseCase
) : ContainerHost<FolderDetailState, FolderDetailSideEffect>, ViewModel() {
    
    private val folderId: Long = savedStateHandle.get<Long>("folderId") ?: 0L
    private val folderName: String = savedStateHandle.get<String>("folderName") ?: ""

    override val container: Container<FolderDetailState, FolderDetailSideEffect> = container(
        FolderDetailState(
            folderId = folderId,
            folderName = folderName
        )
    )

    init {
        loadFolderDetail()
    }

    private fun loadFolderDetail() = intent {
        reduce { state.copy(isLoading = true) }
        
        viewModelScope.launch {
            getFolderDetailUseCase(folderId)
                .catch { error ->
                    reduce { state.copy(isLoading = false, error = error.message) }
                    postSideEffect(FolderDetailSideEffect.ShowError(error.message ?: "폴더 상세 조회에 실패했습니다"))
                }
                .collectLatest { images ->
                    reduce { 
                        state.copy(
                            images = images.toPersistentList(),
                            isLoading = false,
                            error = null
                        )
                    }
                }
        }
    }
    
    fun hideBottomSheet() = intent {
        reduce { state.copy(isShowBottomSheet = false) }
    }
    
    fun showBottomSheet() = intent {
        reduce { state.copy(isShowBottomSheet = true) }
    }
}