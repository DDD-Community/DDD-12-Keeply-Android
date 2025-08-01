package com.keeply.presentation.ui.folder.add

import androidx.lifecycle.ViewModel
import com.keeply.domain.folder.usecase.CreateFolderUseCase
import com.keeply.presentation.core.components.colorBar.FolderColor
import com.keeply.presentation.core.components.colorBar.toHexString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AddFolderViewModel @Inject constructor(
    private val createFolderUseCase: CreateFolderUseCase
): ViewModel(), ContainerHost<AddFolderState, AddFolderSideEffect> {
    override val container: Container<AddFolderState, AddFolderSideEffect> =
        container(AddFolderState())
    
    fun updateFolderName(name: String) = intent {
        reduce { state.copy(folderName = name) }
    }
    
    fun selectColor(color: FolderColor) = intent {
        reduce { state.copy(folderColor = color) }
    }
    
    fun createFolder() = intent {
        val folderName = state.folderName
        val color = state.folderColor.toHexString()

        if (state.checkFolderRegex()) {
            createFolderUseCase(folderName, color)
                .catch { exception ->
                    val errorMessage = exception.message ?: "폴더 생성에 실패했습니다."
                    postSideEffect(AddFolderSideEffect.ShowError(errorMessage))
                }
                .collect { folder ->
                    postSideEffect(AddFolderSideEffect.ShowCreateSuccess)
                }
        } else {
            postSideEffect(AddFolderSideEffect.ShowError("폴더 이름을 입력해주세요."))
        }

    }
}