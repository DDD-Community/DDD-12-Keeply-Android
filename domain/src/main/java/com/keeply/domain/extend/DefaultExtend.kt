package com.keeply.domain.extend

import java.util.Date

fun String?.default() = this ?: ""
fun Int?.default() = this ?: 0
fun <T> List<T>?.default() = this ?: emptyList()
fun <T, K> Map<T, K>?.default() = this ?: mapOf()
fun Boolean?.default() = this ?: false
fun Float?.default() = this ?: 0f
fun Double?.default() = this ?: 0.0
fun Long?.default() = this ?: 0L
fun Long?.idDefault() = this ?: -1
fun Date?.default() = this ?: Date()