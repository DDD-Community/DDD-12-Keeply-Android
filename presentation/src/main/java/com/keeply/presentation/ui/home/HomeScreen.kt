package com.keeply.presentation.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.keeply.presentation.core.components.Tag
import com.keeply.presentation.core.theme.LocalColors
import com.keeply.presentation.core.theme.LocalTypography
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()

    HomeScreen(
        name = uiState.name,
        isTagChecked = uiState.isTagChecked,
        onCheckedChange = viewModel::tagCheckedChange
    )
}

@Composable
fun HomeScreen(
    name: String,
    isTagChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            name,
            color = LocalColors.current.success,
            style = LocalTypography.current.header01
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
        name = "Keeply",
        isTagChecked = false,
        onCheckedChange = {}
    )
}
