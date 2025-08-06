package com.keeply.presentation.ui.scan.scanafter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.keeply.domain.image.usecase.CreateImageUseCase
import com.keeply.presentation.ui.scan.navigation.ScanRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ScanAfterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createImageUseCase: CreateImageUseCase
) : ContainerHost<ScanAfterState, ScanAfterSideEffect>, ViewModel() {
    
    private val scanAfter: ScanRoute.ScanAfter = savedStateHandle.toRoute()
    
    override val container: Container<ScanAfterState, ScanAfterSideEffect> =
        container(
            ScanAfterState(
                uri = scanAfter.url,
                cachedImageId = scanAfter.cachedImageId ?: "",
                detectedText = scanAfter.detectedText,
                recommendedTags = scanAfter.recommendedTags
            )
        )
    
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
                postSideEffect(ScanAfterSideEffect.NavigateToSuccess(image.imageId))
            }
        }
    }
}