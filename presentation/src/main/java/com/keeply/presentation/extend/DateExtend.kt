package com.keeply.presentation.extend

import java.text.SimpleDateFormat
import java.util.Locale

fun String.formatDate(
    format: String = "yyyy.MM.dd",
): String =
    try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat(format, Locale.getDefault())
        val date = inputFormat.parse(this)
        date?.let { outputFormat.format(it) } ?: this
    } catch (e: Exception) {
        this
    }
