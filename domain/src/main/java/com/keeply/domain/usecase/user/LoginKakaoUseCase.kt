package com.keeply.domain.usecase.user

import com.keeply.domain.model.LoginKakao
import com.keeply.domain.model.Token
import kotlinx.coroutines.flow.Flow

interface LoginKakaoUseCase {
    suspend operator fun invoke(loginKakao: LoginKakao): Flow<Token>
}