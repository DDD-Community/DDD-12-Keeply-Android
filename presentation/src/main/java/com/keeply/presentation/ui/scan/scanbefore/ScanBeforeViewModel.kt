package com.keeply.presentation.ui.scan.scanbefore

import android.net.Uri
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
class ScanBeforeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
):ContainerHost<ScanBeforeState, ScanBeforeSideEffect>, ViewModel() {
    val scanBefore: ScanRoute.ScanBefore = savedStateHandle.toRoute()

    override val container: Container<ScanBeforeState, ScanBeforeSideEffect> =
        container(ScanBeforeState(
            Uri.decode(scanBefore.url)
        ))

}