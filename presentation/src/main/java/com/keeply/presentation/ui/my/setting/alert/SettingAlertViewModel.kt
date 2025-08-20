package com.keeply.presentation.ui.my.setting.alert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keeply.domain.extend.default
import com.keeply.domain.model.NotificationSetting
import com.keeply.domain.usecase.user.GetNotificationSettingUseCase
import com.keeply.domain.usecase.user.UpdateNotificationSettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SettingAlertViewModel @Inject constructor(
    private val getNotificationSettingUseCase: GetNotificationSettingUseCase,
    private val updateNotificationSettingUseCase: UpdateNotificationSettingUseCase
): ContainerHost<SettingAlertState, SettingAlertSideEffect>, ViewModel()  {
    override val container: Container<SettingAlertState, SettingAlertSideEffect> =
        container(SettingAlertState())

    init {
        loadNotificationSettings()
    }

    fun loadNotificationSettings() = intent {
        viewModelScope.launch {
            getNotificationSettingUseCase()
                .onStart { reduce { state.copy(isShowLoading = true) } }
                .onCompletion { reduce { state.copy(isShowLoading = false) } }
                .catch { error ->
                    postSideEffect(SettingAlertSideEffect.ShowError(error.message.default()))
                }.collect { notificationSetting ->
                    reduce {
                        state.copy(
                            allowStorageNotification = notificationSetting.allowStorageNotification,
                            allowMarketingNotification = notificationSetting.allowMarketingNotification
                        )
                    }
                }
        }
    }

    fun updateStorageNotification(enabled: Boolean) = intent {
        val notificationSetting = NotificationSetting(
            allowStorageNotification = enabled,
            allowMarketingNotification = state.allowMarketingNotification
        )
        updateNotificationSetting(notificationSetting)
    }

    fun updateMarketingNotification(enabled: Boolean) = intent {
        val notificationSetting = NotificationSetting(
            allowStorageNotification = state.allowStorageNotification,
            allowMarketingNotification = enabled
        )
        updateNotificationSetting(notificationSetting)
    }

    private fun updateNotificationSetting(notificationSetting: NotificationSetting) = intent {
        viewModelScope.launch {
            updateNotificationSettingUseCase(notificationSetting)
                .onStart { reduce { state.copy(isShowLoading = true) } }
                .onCompletion { reduce { state.copy(isShowLoading = false) } }
                .catch { error ->
                    postSideEffect(SettingAlertSideEffect.ShowError(error.message.default()))
                }.collect { updatedSetting ->
                    reduce { 
                        state.copy(
                            allowStorageNotification = updatedSetting.allowStorageNotification,
                            allowMarketingNotification = updatedSetting.allowMarketingNotification
                        )
                    }
                }
        }
    }
}