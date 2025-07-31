package com.keeply.presentation.ui.scan.scanbefore

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.keeply.domain.model.ScanImage
import com.keeply.domain.usecase.scan.GetScanOnBoardingVisibilityUseCase
import com.keeply.domain.usecase.scan.ScanImageUseCase
import com.keeply.domain.usecase.scan.SetDoNotShowDialogUseCase
import com.keeply.presentation.ui.scan.navigation.ScanRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ScanBeforeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    getScanOnBoardingVisibilityUseCase: GetScanOnBoardingVisibilityUseCase,
    private val setDoNotShowDialogUseCase: SetDoNotShowDialogUseCase,
    private val scanImageUseCase: ScanImageUseCase
) : ContainerHost<ScanBeforeState, ScanBeforeSideEffect>, ViewModel() {
    val scanBefore: ScanRoute.ScanBefore = savedStateHandle.toRoute()

    fun onValueChange(value: String) = intent {
        reduce {
            state.copy(
                textField = value
            )
        }
    }

    override val container: Container<ScanBeforeState, ScanBeforeSideEffect> =
        container(
            ScanBeforeState(
                Uri.decode(scanBefore.url)
            )
        )

    // 온보딩 모달 노출 여부
    val showOnBoardingModal = getScanOnBoardingVisibilityUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    fun onDoNotShowAgain() {
        viewModelScope.launch {
            setDoNotShowDialogUseCase(true)
        }
    }

    fun scanImage(
        image: String?,
        successCallback: () -> Unit
    ) = viewModelScope.launch {
        if (image == null) return@launch

        scanImageUseCase(
            ScanImage(
                isNew = true,
                file = image
            )
        ).catch {
            it.stackTrace
            Log.e("ERROR", it.toString())
        }.collect {
            successCallback()
            Log.d("euzl", "scanImage: $it") // for test
        }
    }
}