package com.keeply.presentation.ui.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {
    override val container: Container<HomeState, HomeSideEffect> = container(HomeState())

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