package com.keeply.presentation.ui.folder

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.keeply.presentation.R
import com.keeply.presentation.core.components.KeeplyAppBar
import com.keeply.presentation.core.components.KeeplyTab
import com.keeply.presentation.core.theme.KeeplyTheme
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun FolderRoute(
    viewModel: FolderViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()

    FolderScreen(
        uiState = uiState,
        onClickTab = viewModel::onClickTab
    )
}

@Composable
fun FolderScreen(
    uiState: FolderState,
    onClickTab: (Int) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(KeeplyTheme.colors.neutral100)
    ) {
        KeeplyAppBar(
            customTitleContent = {
                Image(
                    modifier = Modifier
                        .width(72.dp)
                        .align(Alignment.CenterStart),
                    painter = painterResource(R.drawable.ic_keeply_text_logo),
                    contentDescription = "Keeply"
                )
            },
            leadingIcon = null,
            trailingIcon = {
                Image(
                    painter = KeeplyTheme.icons.alarmStateOn,
                    contentDescription = "Notification"
                )
            },
            onClickTrailing = {}
        )

        KeeplyTab(
            selectedTabIndex = uiState.selectedTab.index,
            tabs = uiState.tabs,
            onClick = onClickTab
        )

        when(uiState.selectedTab) {
            FolderTabs.Folder -> FolderDisplay()
            FolderTabs.Uncategorized -> UncategorizedDisplay()
        }
    }
}

@Preview
@Composable
fun FolderScreenPreview() {
    KeeplyTheme {
        FolderScreen(
            uiState = FolderState()
        )
    }
}

