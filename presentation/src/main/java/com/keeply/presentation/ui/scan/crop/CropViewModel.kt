package com.keeply.presentation.ui.scan.crop

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
class CropViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ContainerHost<CropState, CropSideEffect>,ViewModel() {
    
    private val scanCrop = savedStateHandle.toRoute<ScanRoute.ScanCrop>()
    
    override val container: Container<CropState, CropSideEffect> = container(
        CropState(uri = scanCrop.url)
    )

}