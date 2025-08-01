package com.keeply.data.core.extension

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import retrofit2.HttpException

/**
 * 에러 응답을 위한 간단한 데이터 클래스
 * BaseResponse의 nullable 이슈를 회피하기 위해 별도로 정의
 */
@Serializable
private data class ErrorResponse(
    val success: Boolean? = null,
    val reason: String? = null
)

/**
 * HttpException에서 에러 메시지를 추출하는 확장 함수
 * BaseResponse 형식의 에러 응답을 파싱하여 reason 필드를 반환
 * 
 * @param json Json 파서 인스턴스
 * @param defaultMessage 파싱 실패 시 사용할 기본 메시지
 * @return 에러 메시지
 */
fun HttpException.getErrorMessage(
    json: Json,
    defaultMessage: String = "요청 처리에 실패했습니다."
): String {
    val errorBody = response()?.errorBody()?.string()
    
    return if (errorBody != null) {
        try {
            val errorResponse = json.decodeFromString<ErrorResponse>(errorBody)
            errorResponse.reason ?: "$defaultMessage (${code()})"
        } catch (e: Exception) {
            "$defaultMessage (${code()})"
        }
    } else {
        "$defaultMessage (${code()})"
    }
}

/**
 * HttpException을 에러 메시지와 함께 Exception으로 변환
 */
fun HttpException.toException(
    json: Json,
    defaultMessage: String = "요청 처리에 실패했습니다."
): Exception {
    return Exception(getErrorMessage(json, defaultMessage))
}