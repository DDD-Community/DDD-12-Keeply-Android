package com.keeply.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.R
import com.keeply.presentation.core.navigation.KeeplyTab
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    selectedTab: KeeplyTab = KeeplyTab.HOME,
    onTabSelected: (KeeplyTab) -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(
                color = KeeplyTheme.colors.neutralBlack,
                shape = RoundedCornerShape(50)
            ).height(68.dp)
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavigationBarItem(
            icon = KeeplyTheme.icons.home,
            isSelected = selectedTab == KeeplyTab.HOME,
            contentDescription = KeeplyTab.HOME.name,
            onClick = { onTabSelected(KeeplyTab.HOME) }
        )

        NavigationBarItem(
            icon = KeeplyTheme.icons.folderFilled,
            isSelected = selectedTab == KeeplyTab.FOLDER,
            contentDescription = KeeplyTab.FOLDER.name,
            onClick = { onTabSelected(KeeplyTab.FOLDER) }
        )

        NavigationBarCenterItem(
            onTabSelected = onTabSelected
        )

        NavigationBarItem(
            icon = KeeplyTheme.icons.timer,
            isSelected = selectedTab == KeeplyTab.ALARM,
            contentDescription = KeeplyTab.ALARM.name,
            onClick = { onTabSelected(KeeplyTab.ALARM) }
        )

        NavigationBarItem(
            icon = KeeplyTheme.icons.user,
            isSelected = selectedTab == KeeplyTab.MY,
            contentDescription = KeeplyTab.MY.name,
            onClick = { onTabSelected(KeeplyTab.MY) }
        )
    }
}

@Composable
private fun NavigationBarCenterItem(
    onTabSelected: (KeeplyTab) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(KeeplyTheme.colors.orange400)
            .clickable { onTabSelected(KeeplyTab.SCAN) },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_add),
            contentDescription = "Scan",
            modifier = Modifier.size(28.dp),
            tint = Color.White
        )
    }
}

@Composable
private fun NavigationBarItem(
    icon: Painter,
    isSelected: Boolean,
    contentDescription: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(CircleShape)
            .clickable(
                onClick = onClick
            )
            .width(50.dp)
            .height(52.dp)
            .padding(
                top = 12.dp,
                start = 14.dp,
                end = 14.dp,
                bottom = 6.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp),
            tint = if (isSelected) KeeplyTheme.colors.neutral100 else KeeplyTheme.colors.neutral700
        )
        
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(KeeplyTheme.colors.orange400)
            )
        }
    }
}

@Preview
@Composable
private fun NavigationBarPreview() {
    KeeplyTheme {
        NavigationBar(
            selectedTab = KeeplyTab.HOME,
            onTabSelected = {}
        )
    }
}