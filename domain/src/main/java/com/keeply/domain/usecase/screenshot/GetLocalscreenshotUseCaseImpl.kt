package com.keeply.domain.usecase.screenshot

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.keeply.domain.model.Screenshot
import com.keeply.domain.repository.LocalScreenshotRepository

class GetLocalScreenshotsUseCaseImpl(
    private val repository: LocalScreenshotRepository
) : GetLocalScreenshotsUseCase {
    override fun invoke(): Pager<Int, Screenshot> {
        return Pager(
            config = PagingConfig(pageSize = 100),
            pagingSourceFactory = { repository.getScreenshots() }
        )
    }
}