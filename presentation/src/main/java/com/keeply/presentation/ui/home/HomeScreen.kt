package com.keeply.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.keeply.presentation.core.components.InsightTextField
import com.keeply.presentation.core.components.KeeplyTab
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.core.components.Tag
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
        textField = uiState.textField,
        textFieldMaxLength = uiState.textFieldMaxLength,
        isTagChecked = uiState.isTagChecked,
        onCheckedChange = viewModel::tagCheckedChange,
        onClickTab = viewModel::onClickTab,
        onValueChange = viewModel::onValueChange
    )
}

@Composable
fun HomeScreen(
    tabs: ImmutableList<String>,
    selectedTabIndex: Int,
    textField: String,
    textFieldMaxLength: Int,
    isTagChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClickTab: (Int) -> Unit,
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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

        InsightTextField(
            value = textField,
            placeholder = "인사이트를 적어주세요.",
            onValueChange = onValueChange,
            maxLength = textFieldMaxLength
        )

        Icon(
            painter = KeeplyTheme.icons.checkmark,
            contentDescription = "체크아이콘",
            tint = KeeplyTheme.colors.orange1000
        )

        Tag(
            label = "Label",
            checked = isTagChecked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        tabs = persistentListOf("Keeply", "안칠수"),
        selectedTabIndex = 0,
        textField = "",
        textFieldMaxLength = 300,
        isTagChecked = false,
        onCheckedChange = {},
        onClickTab = {},
        onValueChange = {}
    )
}
