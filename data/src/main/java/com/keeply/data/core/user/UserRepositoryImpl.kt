package com.keeply.data.core.user

import com.keeply.data.core.user.mapper.toDomain
import com.keeply.data.core.user.mapper.toRequest
import com.keeply.domain.model.LoginKakao
import com.keeply.domain.model.Token
import com.keeply.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    val userService: UserService,
) : UserRepository {
    override suspend fun loginKakao(loginKakao: LoginKakao): Flow<Token> = flow {
        emit(userService.loginKakao(loginKakao.toRequest()).toDomain())
    }

}