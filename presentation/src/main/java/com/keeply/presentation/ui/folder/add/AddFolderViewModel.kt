package com.keeply.presentation.ui.folder.add

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AddFolderViewModel @Inject constructor(): ViewModel(), ContainerHost<AddFolderState, AddFolderSideEffect> {
    override val container: Container<AddFolderState, AddFolderSideEffect> =
        container(AddFolderState(
        ))
}