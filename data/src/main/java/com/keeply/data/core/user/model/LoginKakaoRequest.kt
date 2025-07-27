package com.keeply.data.core.user.model

import java.util.Date

data class LoginKakaoRequest(
    val id: Long?,
    val connected_at: Date?,
    val kakao_account: Account?,
    val fcmToken: String?
) {
    data class Account(
        val profile_needs_agreement : Boolean?,
        val profile_nickname_needs_agreement : Boolean?,
        val profile_image_needs_agreement : Boolean?,
        val email_needs_agreement : Boolean?,
        val profile : Profile?,
        val email : String?,
    )

    data class Profile(
        val nickname: String?,
        val thumbnail_image_url: String?,
        val profile_image_url: String?,
    )
}