package com.keeply.presentation.ui.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keeply.domain.extend.default
import com.keeply.domain.usecase.user.GetUserInfoUseCase
import com.keeply.domain.usecase.user.LogoutUseCase
import com.keeply.domain.usecase.user.WithdrawUseCase
import com.keeply.presentation.core.navigation.KeeplyNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val withdrawUseCase: WithdrawUseCase,
) : ContainerHost<MyState, MySideEffect>, ViewModel() {

    override val container = container<MyState, MySideEffect>(MyState())

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() = intent {
        getUserInfoUseCase().catch { error ->
            postSideEffect(MySideEffect.ShowError(error.message.default()))
        }.collect { userInfo ->
            reduce { state.copy(
                userEmail = userInfo.email,
                userNickname = userInfo.nickname,
                userImage = userInfo.image
            ) }
        }
    }

    fun logout() = intent {
        logoutUseCase().catch { error ->
            postSideEffect(MySideEffect.ShowError(error.message.default()))
        }.collect {
            postSideEffect(MySideEffect.SuccessLogout)
        }
    }

    fun withdraw() = intent {
        withdrawUseCase().catch { error ->
            postSideEffect(MySideEffect.ShowError(error.message.default()))
        }.collect {
            postSideEffect(MySideEffect.SuccessWithdraw)
        }
    }

    fun showWithdrawModal() = intent {
        reduce { state.copy(
            isShowWithdrawModal = true
        ) }
    }

    fun dismissWithdrawModal() = intent {
        reduce { state.copy(
            isShowWithdrawModal = false
        ) }
    }
}

