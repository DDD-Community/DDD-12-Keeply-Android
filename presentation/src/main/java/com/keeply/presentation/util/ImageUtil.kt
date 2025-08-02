package com.keeply.presentation.util

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream

fun Uri.getFileName(context: Context): String? {
    val returnCursor = context.contentResolver.query(this, null, null, null, null)
    returnCursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (it.moveToFirst()) {
            return it.getString(nameIndex)
        }
    }
    return null
}

fun Uri.getFileExtension(context: Context): String {
    return getFileName(context)?.substringAfterLast('.', "") ?: "tmp"
}

fun Uri.toFile(context: Context): File {
    val extension = getFileExtension(context)
    val file = File.createTempFile("upload", ".$extension", context.cacheDir)
    context.contentResolver.openInputStream(this)?.use { input ->
        FileOutputStream(file).use { output -> input.copyTo(output) }
    }
    return file
}
