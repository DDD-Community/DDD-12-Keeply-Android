package com.keeply.presentation.ui.scan.crop

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class CropViewModel @Inject constructor(): ContainerHost<CropState, CropSideEffect>,ViewModel() {
    override val container: Container<CropState, CropSideEffect> = container(CropState())


}