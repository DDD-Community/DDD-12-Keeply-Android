package com.keeply.data.user.mapper

import com.keeply.data.user.model.TokenResponse
import com.keeply.domain.extend.default
import com.keeply.domain.model.Token

fun TokenResponse?.toDomain() = Token(
    accessToken = this?.accessToken.default(),
    refreshToken = this?.refreshToken.default()
)