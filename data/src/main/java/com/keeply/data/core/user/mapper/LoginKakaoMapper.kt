package com.keeply.data.core.user.mapper

import com.keeply.data.core.user.model.LoginKakaoRequest
import com.keeply.domain.model.LoginKakao

fun LoginKakao.toRequest() = LoginKakaoRequest(
    id = id,
    connected_at = connected_at,
    kakao_account = kakao_account?.toRequest(),
    fcmToken = fcmToken
)

fun LoginKakao.Account.toRequest() = LoginKakaoRequest.Account(
    profile_needs_agreement = profile_needs_agreement,
    profile_nickname_needs_agreement = profile_nickname_needs_agreement,
    profile_image_needs_agreement = profile_image_needs_agreement,
    email_needs_agreement = email_needs_agreement,
    profile = profile?.toRequest(),
    email = email
)

fun LoginKakao.Profile.toRequest() = LoginKakaoRequest.Profile(
    nickname = nickname,
    thumbnail_image_url = thumbnail_image_url,
    profile_image_url = profile_image_url

)