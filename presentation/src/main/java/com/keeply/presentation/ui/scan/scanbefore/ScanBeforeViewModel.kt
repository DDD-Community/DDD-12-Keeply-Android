package com.keeply.presentation.ui.scan.scanbefore

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.keeply.domain.model.ScanAnalyze
import com.keeply.domain.usecase.scan.ScanImageUseCase
import com.keeply.domain.usecase.scan.SetDoNotShowDialogUseCase
import com.keeply.presentation.ui.scan.navigation.ScanRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ScanBeforeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val setDoNotShowDialogUseCase: SetDoNotShowDialogUseCase,
    private val scanImageUseCase: ScanImageUseCase
) : ContainerHost<ScanBeforeState, ScanBeforeSideEffect>, ViewModel() {

    private val scanBefore: ScanRoute.ScanBefore = savedStateHandle.toRoute()

    override val container: Container<ScanBeforeState, ScanBeforeSideEffect> =
        container(
            ScanBeforeState(
                uri = Uri.decode(scanBefore.url),
                isShowOnBoarding = scanBefore.isShowOnBoarding
            )
        )

    fun onBoardingConfirmClicked() = intent {
        reduce {
            state.copy(
                isShowOnBoarding = false
            )
        }
    }

    fun onBoardingNeverShowClicked() {
        onBoardingConfirmClicked()
        viewModelScope.launch {
            setDoNotShowDialogUseCase(true)
        }
    }

    fun scanImage(
        image: File?,
        isSkip: Boolean = false,
        successCallback: (ScanAnalyze) -> Unit
    ) = viewModelScope.launch {
        if (image == null) return@launch

        onScanComplete(false)
        scanImageUseCase(
            isNew = true,
            imageId = null,
            isSkip = isSkip,
            file = image
        ).catch {
            it.stackTrace
            Log.e("ERROR", it.toString())
        }.collect {
            onScanComplete(true)
            successCallback(it)
            onOcrResult(it)
        }
    }

    private fun onScanComplete(done: Boolean) = intent {
        reduce {
            state.copy(
                isScanCompleted = done
            )
        }
    }

    private fun onOcrResult(scanAnalyze: ScanAnalyze) = intent {
        reduce {
            state.copy(
                ocrResult = scanAnalyze
            )
        }
    }
}