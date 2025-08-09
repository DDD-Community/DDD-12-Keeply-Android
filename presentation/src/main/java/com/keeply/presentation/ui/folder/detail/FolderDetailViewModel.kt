package com.keeply.presentation.ui.folder.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keeply.domain.folder.usecase.DeleteFolderUseCase
import com.keeply.domain.folder.usecase.GetFolderDetailUseCase
import com.keeply.domain.folder.usecase.UpdateFolderUseCase
import com.keeply.presentation.core.components.colorBar.FolderColor
import com.keeply.presentation.core.components.colorBar.fromHexString
import com.keeply.presentation.core.components.colorBar.toHexString
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
    private val getFolderDetailUseCase: GetFolderDetailUseCase,
    private val updateFolderUseCase: UpdateFolderUseCase,
    private val deleteFolderUseCase: DeleteFolderUseCase
) : ContainerHost<FolderDetailState, FolderDetailSideEffect>, ViewModel() {
    
    private val folderId: Long = savedStateHandle.get<Long>("folderId") ?: 0L
    private val folderName: String = savedStateHandle.get<String>("folderName") ?: ""
    private val folderColor: String = savedStateHandle.get<String>("folderColor") ?: "YELLOW"

    override val container: Container<FolderDetailState, FolderDetailSideEffect> = container(
        FolderDetailState(
            folderId = folderId,
            folderName = folderName,
            editingFolderName = folderName,
            selectedColor = fromHexString(folderColor) ?: FolderColor.YELLOW
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
    
    fun updateFolderName(name: String) = intent {
        reduce { state.copy(editingFolderName = name) }
    }
    
    fun selectColor(color: FolderColor) = intent {
        reduce { state.copy(selectedColor = color) }
    }
    
    fun saveFolder() = intent {
        viewModelScope.launch {
            updateFolderUseCase(
                folderId = state.folderId,
                folderName = state.editingFolderName,
                color = state.selectedColor.toHexString()
            )
                .catch { error ->
                    postSideEffect(FolderDetailSideEffect.ShowError(error.message ?: "폴더 수정에 실패했습니다"))
                }
                .collectLatest { updatedFolder ->
                    reduce { 
                        state.copy(
                            folderName = updatedFolder.folderName,
                            isShowBottomSheet = false,
                            hasUpdated = true
                        )
                    }
                    loadFolderDetail()
                }
        }
    }
    
    fun showDeleteDialog() = intent {
        reduce { state.copy(isShowDeleteDialog = true) }
    }
    
    fun hideDeleteDialog() = intent {
        reduce { state.copy(isShowDeleteDialog = false) }
    }
    
    fun deleteFolder() = intent {
        viewModelScope.launch {
            deleteFolderUseCase(state.folderId)
                .catch { error ->
                    reduce { state.copy(isShowDeleteDialog = false) }
                    postSideEffect(FolderDetailSideEffect.ShowError(error.message ?: "폴더 삭제에 실패했습니다"))
                }
                .collectLatest { success ->
                    if (success) {
                        postSideEffect(FolderDetailSideEffect.NavigateBackWithRefresh)
                    }
                }
        }
    }
}