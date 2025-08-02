package com.keeply.presentation.ui.scan.scanafter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.keeply.presentation.ui.scan.navigation.ScanRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ScanAfterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ContainerHost<ScanAfterState, ScanAfterSideEffect>, ViewModel() {
    
    private val scanAfter: ScanRoute.ScanAfter = savedStateHandle.toRoute()
    
    override val container: Container<ScanAfterState, ScanAfterSideEffect> =
        container(
            ScanAfterState(
                uri = scanAfter.url,
                cachedImageId = scanAfter.cachedImageId,
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
}