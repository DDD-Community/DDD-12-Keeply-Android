package com.keeply.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.keeply.presentation.core.components.KeeplyTab
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()

    HomeScreen(
        tabs = uiState.tabs,
        selectedTabIndex = uiState.selectedTabIndex,
        onClickTab = viewModel::onClickTab
    )
}

@Composable
fun HomeScreen(
    tabs: ImmutableList<String>,
    selectedTabIndex: Int,
    onClickTab: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        KeeplyTab(
            selectedTabIndex = selectedTabIndex,
            tabs = tabs,
            onClick = onClickTab
        )

        KeeplyText(
            text = tabs[selectedTabIndex],
            style = KeeplyTheme.typography.header01,
            color = KeeplyTheme.colors.success
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        tabs = persistentListOf("Keeply", "안칠수"),
        selectedTabIndex = 0,
        onClickTab = {}
    )
}
