package com.keeply.presentation.ui.folder.screenshot

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.keeply.domain.image.usecase.GetImageInfoUseCase
import com.keeply.presentation.core.navigation.FolderRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class DetailScreenshotViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getImageInfoUseCase: GetImageInfoUseCase
) : ContainerHost<DetailScreenshotState, DetailScreenshotSideEffect>, ViewModel() {

    private val detailScreenshot: FolderRoute.DetailScreenshot = savedStateHandle.toRoute()

    override val container: Container<DetailScreenshotState, DetailScreenshotSideEffect> =
        container(
            DetailScreenshotState(
                imageId = detailScreenshot.imageId,
                folderName = detailScreenshot.folderName,
                folderColor = detailScreenshot.folderColor
            )
        )

    init {
        loadInfo()
    }

    private fun loadInfo() = intent {
        viewModelScope.launch {
            getImageInfoUseCase(imageId = state.imageId)
                .onStart {
                    reduce {
                        state.copy(isLoading = true)
                    }
                }
                .catch { e ->
                    reduce {
                        state.copy(
                            isLoading = false,
                            error = e.message
                        )
                    }
                }
                .collect { image ->
                    reduce {
                        state.copy(
                            isLoading = false,
                            presignedUrl = image.presignedUrl,
                            textField = image.insight,
                            tag = image.tag
                        )
                    }

                }
        }
    }

    fun onTextChange(value: String) = intent {
        reduce {
            state.copy(
                textField = value
            )
        }
    }
}