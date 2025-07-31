package com.keeply.presentation.ui.folder.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.keeply.presentation.core.components.colorBar.ColorBar
import com.keeply.presentation.core.components.FolderIcon
import com.keeply.presentation.core.components.KeeplyAppBar
import com.keeply.presentation.core.components.KeeplyTextField
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.core.components.colorBar.FolderColor
import com.keeply.presentation.core.components.colorBar.toComposeColor
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun AddFolderRoute(
    viewModel: AddFolderViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val state by viewModel.collectAsState()
    
    AddFolderScreen(
        state = state,
        onFolderNameChange = viewModel::updateFolderName,
        onColorSelect = viewModel::selectColor,
        onCreateFolder = viewModel::createFolder,
        onNavigateBack = onNavigateBack
    )
}

@Composable
fun AddFolderScreen(
    state: AddFolderState = AddFolderState(),
    onFolderNameChange: (String) -> Unit = {},
    onColorSelect: (FolderColor) -> Unit = {},
    onCreateFolder: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(KeeplyTheme.colors.neutral100)
    ) {
        KeeplyAppBar(
            title = "새 폴더",
            leadingIcon = {
                Icon(
                    painter = KeeplyTheme.icons.chevronLeft,
                    contentDescription = "Back",
                    tint = KeeplyTheme.colors.neutralBlack
                )
            },
            trailingText = "완료",
            onClickLeading = onNavigateBack,
            onClickTrailing = onCreateFolder
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FolderIcon(
                    iconModifier = Modifier
                        .width(100.dp)
                        .height(75.dp),
                    tint = state.folderColor.toComposeColor()
                )

                KeeplyTextField(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .background(KeeplyTheme.colors.neutralWhite)
                        .fillMaxWidth(),
                    value = state.folderName,
                    onValueChange = onFolderNameChange,
                    placeholder = "새 폴더"
                )

                ColorBar(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    selectedColor = state.folderColor,
                    onColorSelected = onColorSelect
                )
            }
        }
    }
}

@Preview
@Composable
fun AddFolderScreenPreview() {
    KeeplyTheme {
        AddFolderScreen()
    }
}