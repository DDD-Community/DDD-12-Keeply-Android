package com.keeply.presentation.ui.folder

import androidx.compose.runtime.Immutable
import com.keeply.domain.folder.model.Folder
import com.keeply.domain.folder.model.FolderImage
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class FolderState(
    val tabs: ImmutableList<String> = persistentListOf("폴더", "미분류 스크린샷"),
    val selectedTab: FolderTabs = FolderTabs.Folder,
    val folders: ImmutableList<Folder> = persistentListOf(),
    val uncategorizedImages: ImmutableList<FolderImage> = persistentListOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)

enum class FolderTabs(val displayName: String, val index: Int) {
    Folder(
        displayName = "폴더",
        index = 0
    ),
    Uncategorized(
        displayName = "미분류 스크린샷",
        index = 1
    );

    companion object {
        fun fromIndex(index: Int) = FolderTabs.entries.find { it.index == index } ?: Folder
    }
}

sealed interface FolderSideEffect