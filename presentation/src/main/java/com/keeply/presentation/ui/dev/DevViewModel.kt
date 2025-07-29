package com.keeply.presentation.ui.dev

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class DevViewModel @Inject constructor() : ContainerHost<DevState, DevSideEffect>, ViewModel() {
    override val container: Container<DevState, DevSideEffect> = container(DevState())

    fun onClickTab(index: Int) = intent {
        reduce {
            state.copy(
                selectedTabIndex = index
            )
        }
    }

    fun onValueChange(value: String) = intent {
        reduce {
            state.copy(
                textField = value
            )
        }
    }
    fun tagCheckedChange(isCheck: Boolean) = intent {
        reduce {
            state.copy(
                isTagChecked = isCheck
            )
        }
    }
}