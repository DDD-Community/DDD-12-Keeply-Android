package com.keeply.data.user.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginKakaoRequest(
    val id: Long?,
    val connected_at: String?,
    val kakao_account: Account?,
    val fcmToken: String?
) {

    @Serializable
    data class Account(
        val profile_needs_agreement : Boolean?,
        val profile_nickname_needs_agreement : Boolean?,
        val profile_image_needs_agreement : Boolean?,
        val email_needs_agreement : Boolean?,
        val profile : Profile?,
        val email : String?,
    )

    @Serializable
    data class Profile(
        val nickname: String?,
        val thumbnail_image_url: String?,
        val profile_image_url: String?,
    )
}