package com.keeply.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.theme.KeeplyTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun KeeplyTab(
    selectedTabIndex: Int,
    tabs: ImmutableList<String>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
) {
    Box {
        HorizontalDivider(
            modifier = modifier
                .fillMaxWidth()
                .height(2.dp)
                .align(Alignment.BottomCenter)
        )

        TabRow(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            indicator = { tabPositions ->
                if (tabPositions.isNotEmpty()) {
                    Box(
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(
                                color = KeeplyTheme.colors.neutralBlack
                            )
                    )
                }
            }
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = selectedTabIndex == index

                Tabs(
                    title = title,
                    index = index,
                    isSelected = isSelected,
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
private fun Tabs(
    title: String,
    index: Int,
    isSelected: Boolean,
    onClick: (Int) -> Unit
) {
    Tab(
        selected = isSelected,
        onClick = { onClick(index) },
        modifier = Modifier
            .height(48.dp)
            .padding(top = 12.dp),
        content = {
            val titleColor: Color = if (isSelected) {
                KeeplyTheme.colors.neutralBlack
            } else {
                KeeplyTheme.colors.neutral400
            }

            KeeplyText(
                text = title,
                style = KeeplyTheme.typography.subtitle01,
                color = titleColor,
                maxLines = 1
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun KeeplyTabPreview() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = persistentListOf("Keeply", "안칠수")
    KeeplyTab(
        selectedTabIndex = selectedTabIndex,
        tabs = tabs,
        onClick = { index ->
            selectedTabIndex = index
        }
    )
}