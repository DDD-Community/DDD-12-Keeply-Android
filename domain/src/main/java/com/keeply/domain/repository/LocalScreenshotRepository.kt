package com.keeply.domain.repository

import androidx.paging.PagingSource
import com.keeply.domain.model.Screenshot

interface LocalScreenshotRepository {
    fun getScreenshots(): PagingSource<Int, Screenshot>
}