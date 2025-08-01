package com.keeply.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.keeply.presentation.core.components.NavigationBar
import com.keeply.presentation.core.navigation.KeeplyNavHost
import com.keeply.presentation.core.navigation.KeeplyTab
import com.keeply.presentation.core.navigation.rememberKeeplyNavigator
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.ui.home.navigation.navigateHome
import com.keeply.presentation.ui.splash.SplashScreen
import com.keeply.presentation.ui.splash.SplashViewModel
import com.keeply.presentation.ui.splash.SplashSideEffect
import com.keeply.presentation.ui.onboarding.navigation.navigateOnboarding
import dagger.hilt.android.AndroidEntryPoint

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
                var isShowSplash by remember { mutableStateOf(true) }
                
                if (isShowSplash) {
                    val splashViewModel: SplashViewModel = hiltViewModel()

                    splashViewModel.collectSideEffect { sideEffect ->
                        when (sideEffect) {
                            is SplashSideEffect.NavigateToHome -> {
                                isShowSplash = false
                            }
                            is SplashSideEffect.NavigateToOnboarding -> {
                                isShowSplash = false
                                keeplyNavigator.navController.navigateOnboarding()
                            }
                        }
                    }
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
                        val currentTab = keeplyNavigator.currentTab ?: KeeplyTab.HOME

                        KeeplyNavHost(keeplyNavigator)

                        if (keeplyNavigator.shouldShowNavigationBar() && currentTab != KeeplyTab.SCAN) {
                            NavigationBar(
                                modifier = Modifier
                                    .padding(bottom = 16.dp)
                                    .align(Alignment.BottomCenter),
                                selectedTab = currentTab,
                                onTabSelected = { tab ->
                                    keeplyNavigator.navigate(
                                        KeeplyTab.entries.find {
                                            it == tab
                                        } ?: KeeplyTab.HOME
                                    )
                                }
                            )
                        }

                        if(isShowSplash) {
                            SplashScreen()
                        }
                    }
                }
            }
        }
    }
}