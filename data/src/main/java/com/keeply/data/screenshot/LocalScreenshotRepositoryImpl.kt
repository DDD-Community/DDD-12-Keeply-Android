package com.keeply.data.screenshot

import android.content.ContentResolver
import androidx.paging.PagingSource
import com.keeply.data.screenshot.paging.ScreenshotPagingSource
import com.keeply.domain.model.Screenshot
import com.keeply.domain.repository.LocalScreenshotRepository

class ScreenshotRepositoryImpl(
    private val contentResolver: ContentResolver
) : LocalScreenshotRepository {

    override fun getScreenshots(): PagingSource<Int, Screenshot> {
        return ScreenshotPagingSource(contentResolver)
    }
}