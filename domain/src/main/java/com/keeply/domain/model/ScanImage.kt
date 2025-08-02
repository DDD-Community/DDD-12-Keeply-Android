package com.keeply.domain.model

import java.io.File

data class ScanImage(
    val isNew: Boolean = true,
    val imageId: Long? = null,
    val file: File? = null
)