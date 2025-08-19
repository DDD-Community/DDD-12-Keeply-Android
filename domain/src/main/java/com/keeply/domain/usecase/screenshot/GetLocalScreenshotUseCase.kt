package com.keeply.domain.usecase.screenshot

import androidx.paging.Pager
import com.keeply.domain.model.Screenshot

interface GetLocalScreenshotsUseCase {
    operator fun invoke(pageSize: Int = 100): Pager<Int, Screenshot>
}