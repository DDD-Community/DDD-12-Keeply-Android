package com.keeply.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.SystemBarStyle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
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
import com.keeply.presentation.ui.splash.SplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )

        setContent {
            KeeplyTheme {
                val backgroundColor = KeeplyTheme.colors.neutral100
                val keeplyNavigator = rememberKeeplyNavigator()
                var selectedTab by remember { mutableStateOf(KeeplyTab.HOME) }
                var isShowSplash by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    delay(2000)
                    isShowSplash = false
                }

                SideEffect {
                    enableEdgeToEdge(
                        statusBarStyle = SystemBarStyle.light(
                            backgroundColor.toArgb(),
                            backgroundColor.toArgb()
                        ),
                        navigationBarStyle = SystemBarStyle.light(
                            backgroundColor.toArgb(),
                            backgroundColor.toArgb()
                        )
                    )
                }

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

                        NavigationBar(
                                modifier = Modifier
                                    .padding(bottom = 16.dp)
                                    .align(Alignment.BottomCenter),
                            selectedTab = selectedTab,
                            onTabSelected = { tab ->
                                selectedTab = tab

                                keeplyNavigator.navigate(
                                    KeeplyTab.entries.find {
                                        it == selectedTab
                                    } ?: KeeplyTab.HOME
                                )
                            }
                        )

                        if(isShowSplash) {
                            SplashScreen()
                        }
                    }
                }
            }
        }
    }
}