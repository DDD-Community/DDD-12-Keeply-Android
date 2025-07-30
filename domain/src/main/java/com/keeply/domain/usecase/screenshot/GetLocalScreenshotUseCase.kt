package com.keeply.domain.usecase.screenshot

import androidx.paging.Pager
import com.keeply.domain.model.Screenshot

interface GetLocalScreenshotsUseCase {
    operator fun invoke(): Pager<Int, Screenshot>
}