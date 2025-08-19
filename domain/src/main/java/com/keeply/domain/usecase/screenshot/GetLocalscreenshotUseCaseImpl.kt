package com.keeply.domain.usecase.screenshot

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.keeply.domain.model.Screenshot
import com.keeply.domain.repository.LocalScreenshotRepository

class GetLocalScreenshotsUseCaseImpl(
    private val repository: LocalScreenshotRepository
) : GetLocalScreenshotsUseCase {
    override fun invoke(pageSize: Int): Pager<Int, Screenshot> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { repository.getScreenshots() }
        )
    }
}