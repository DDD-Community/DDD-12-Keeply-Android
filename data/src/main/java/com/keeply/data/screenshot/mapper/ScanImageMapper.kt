package com.keeply.data.screenshot.mapper

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

//fun ScanImage.toRequest() = ScanImageRequest(
//    isNew = isNew,
//    imageId = imageId,
//    file = file
//)

fun File.toMultipartPart(partName: String = "file"): MultipartBody.Part {
    val requestBody = this.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(partName, this.name, requestBody)
}

fun String.toPlainRequestBody(): RequestBody {
    return this.toRequestBody("text/plain".toMediaTypeOrNull())
}