package com.keeply.data.screenshot.paging

import android.content.ContentResolver
import android.content.ContentUris
import android.os.Build
import android.provider.MediaStore
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.keeply.domain.model.Screenshot

class ScreenshotPagingSource(
    private val contentResolver: ContentResolver
) : PagingSource<Int, Screenshot>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Screenshot> {
        val pageNo = params.key ?: 0
        val pageSize = params.loadSize
        val screenshots = mutableListOf<Screenshot>()

        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media._ID)

        val pathColumn = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.RELATIVE_PATH
        } else {
            MediaStore.Images.Media.DATA
        }

        val selection = "(${MediaStore.Images.Media.MIME_TYPE}=? OR ${MediaStore.Images.Media.MIME_TYPE}=?) AND $pathColumn LIKE ?"
        val selectionArgs: Array<String> = arrayOf("image/png", "image/jpeg", "%Screenshots%")
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC" // 최신 순 정렬

        contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val offset = pageNo * pageSize
            var skipped = 0
            var loaded = 0

            while (cursor.moveToNext()) {
                if (skipped < offset) {
                    skipped++
                    continue
                }
                if (loaded >= pageSize) break

                val id = cursor.getLong(idColumn)
                val contentUri = ContentUris.withAppendedId(uri, id)
                screenshots.add(Screenshot(contentUri))
                loaded++
            }
        }

        return LoadResult.Page(
            data = screenshots,
            prevKey = if (pageNo == 0) null else pageNo - 1,
            nextKey = if (screenshots.isEmpty()) null else pageNo + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Screenshot>): Int? = null
}