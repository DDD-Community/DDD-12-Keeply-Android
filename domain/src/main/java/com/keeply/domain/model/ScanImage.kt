package com.keeply.domain.model

data class ScanImage(
    val isNew: Boolean = true,
    val imageId: Long? = null,
    val file: String? = null
)