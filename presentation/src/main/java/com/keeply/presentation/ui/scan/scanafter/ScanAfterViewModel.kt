package com.keeply.presentation.ui.scan.scanafter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.keeply.domain.folder.usecase.GetFoldersUseCase
import com.keeply.domain.image.usecase.CreateImageUseCase
import com.keeply.presentation.ui.scan.navigation.ScanRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ScanAfterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createImageUseCase: CreateImageUseCase,
    private val getFoldersUseCase: GetFoldersUseCase
) : ContainerHost<ScanAfterState, ScanAfterSideEffect>, ViewModel() {

    private val scanAfter: ScanRoute.ScanAfter = savedStateHandle.toRoute()

    override val container: Container<ScanAfterState, ScanAfterSideEffect> =
        container(
            ScanAfterState(
                uri = scanAfter.url,
                cachedImageId = scanAfter.cachedImageId ?: "",
                detectedTextList = scanAfter.detectedText?.split("\n") ?: emptyList(),
                recommendedTags = scanAfter.recommendedTags
            )
        )

    init {
        loadFolders()
    }

    fun dismissSuccessModal() = intent {
        reduce { state.copy(showSuccessModal = false) }
    }

    fun onFinishedTextSelection() = intent {
        reduce { state.copy(showSelectTextBottomSheet = false) }
    }

    fun updateSelectedIndices(selectedIndices: List<Int>) {
        val selectedText = selectedIndices
            .mapNotNull { idx ->
                container.stateFlow.value.detectedTextList.getOrNull(idx)
            }
            .joinToString("\n")
//            .let { if (it.length > 300) it.take(300) else it } // 엣지케이스 .. 저장 불가 처리

        intent {
            reduce {
                state.copy(
                    textField = selectedText,
                    textFieldLength = selectedText.length
                )
            }
        }
    }

    fun onValueChange(value: String) = intent {
        reduce {
            state.copy(
                textField = value
            )
        }
    }

    fun onSaveClick(selectedText: String, folderId: Long) = intent {
        reduce { state.copy(isLoading = true) }

        viewModelScope.launch {
            createImageUseCase(
                isCached = true,
                cachedImageId = state.cachedImageId,
                imageId = 0,
                imageInsight = selectedText,
                folderId = folderId,
                tag = "Sample"
            ).catch { error ->
                reduce { state.copy(isLoading = false) }
                postSideEffect(ScanAfterSideEffect.ShowError(error.message ?: "이미지 저장에 실패했습니다"))
            }.collectLatest { image ->
                reduce { state.copy(isLoading = false) }
                reduce { state.copy(showSuccessModal = true) }
//                postSideEffect(ScanAfterSideEffect.ShowSuccessModal(image.imageId))
            }
        }
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
}