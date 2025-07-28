package com.keeply.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.components.NavigationBar
import com.keeply.presentation.core.navigation.KeeplyNavHost
import com.keeply.presentation.core.navigation.KeeplyTab
import com.keeply.presentation.core.navigation.rememberKeeplyNavigator
import com.keeply.presentation.core.theme.KeeplyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            KeeplyTheme {
                val keeplyNavigator = rememberKeeplyNavigator()
                var selectedTab by remember { mutableStateOf(KeeplyTab.HOME) }
                var previousSelectedTab by remember { mutableStateOf(KeeplyTab.HOME) }

                if (selectedTab == KeeplyTab.SCAN) {
                    BackHandler {
                        selectedTab = previousSelectedTab
                        keeplyNavigator.navigate(previousSelectedTab)
                    }
                }

                val isNavigationBarVisible = selectedTab != KeeplyTab.SCAN

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsPadding()
                ) { padding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        KeeplyNavHost(keeplyNavigator)

                        if (isNavigationBarVisible) {
                            NavigationBar(
                                modifier = Modifier
                                    .padding(bottom = 16.dp)
                                    .align(Alignment.BottomCenter),
                                selectedTab = selectedTab,
                                onTabSelected = { tab ->
                                    if (tab != selectedTab) {
                                        previousSelectedTab = selectedTab
                                        selectedTab = tab
                                    }

                                    keeplyNavigator.navigate(
                                        KeeplyTab.entries.find {
                                            it == selectedTab
                                        } ?: KeeplyTab.HOME
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}