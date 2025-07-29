package com.keeply.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
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
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.ui.home.component.HomeUncategorizedCard
import com.keeply.presentation.ui.home.component.KeeplyProgressBar
import kotlinx.collections.immutable.persistentListOf
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()

    HomeScreen()
}

@Composable
fun HomeScreen() {
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
                Icon(
                    painter = KeeplyTheme.icons.alarmStateOn,
                    contentDescription = "Notification"
                )
            },
            onClickTrailing = {}
        )

        Column(
            modifier = Modifier
                .padding(
                    top = 20.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 112.dp
                ).verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            KeeplyProgressBar(
                currentLength = 60
            )

            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HomeUncategorizedCard(
                    modifier = Modifier
                        .weight(1f),
                    title = "미분류",
                    count = 7,
                    images = persistentListOf(
                        painterResource(R.drawable.img_onboarding_01),
                        painterResource(R.drawable.img_onboarding_02),
                        painterResource(R.drawable.img_onboarding_03),
                    )
                )

                HomeUncategorizedCard(
                    modifier = Modifier
                        .weight(1f),
                    title = "만료예정",
                    count = 0
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    KeeplyTheme {
        HomeScreen()
    }
}
