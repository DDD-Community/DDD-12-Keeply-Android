package com.keeply.presentation.ui.my

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.keeply.presentation.R
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme
import org.orbitmvi.orbit.compose.collectSideEffect
import android.app.Activity
import android.widget.Toast
import com.keeply.presentation.extend.restartApplication

@Composable
fun MyRoute(
    viewModel: MyViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is MySideEffect.SuccessLogout -> {
                (context as? Activity)?.restartApplication()
            }

            is MySideEffect.ShowError -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    MyScreen(
        isLoading = isLoading,
        onLogoutClick = viewModel::logout
    )
}

@Composable
fun MyScreen(
    isLoading: Boolean = false,
    onLogoutClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(KeeplyTheme.colors.neutral100)
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 24.dp,
                bottom = 112.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        KeeplyText(
            text = stringResource(R.string.my_page_title),
            style = KeeplyTheme.typography.header03
        )

        Row(
            modifier = Modifier
                .padding(top = 12.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(KeeplyTheme.colors.neutralWhite)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(44.dp),
                painter = ColorPainter(KeeplyTheme.colors.neutral200),
                contentDescription = null
            )

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                KeeplyText(
                    text = "닉네임",
                    style = KeeplyTheme.typography.button01Suit
                )

                KeeplyText(
                    text = "Android@gmail.com",
                    style = KeeplyTheme.typography.button02Altform,
                    color = KeeplyTheme.colors.neutral800
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(top = 12.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(KeeplyTheme.colors.neutralWhite)
                .padding(16.dp)
        ) {
            KeeplyText(
                text = stringResource(R.string.my_page_user_settings),
                style = KeeplyTheme.typography.subtitle03,
                color = KeeplyTheme.colors.neutral700
            )

            MyPageMenuItem(
                modifier = Modifier
                    .padding(top = 12.dp),
                text = stringResource(R.string.my_page_notification_settings)
            )

            MyPageMenuItem(
                text = stringResource(R.string.my_page_permission_settings)
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 12.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(KeeplyTheme.colors.neutralWhite)
                .padding(16.dp)
        ) {
            KeeplyText(
                text = stringResource(R.string.my_page_customer_support),
                style = KeeplyTheme.typography.subtitle03,
                color = KeeplyTheme.colors.neutral700
            )

            MyPageMenuItem(
                modifier = Modifier
                    .padding(top = 12.dp),
                text = stringResource(R.string.my_page_inquiry)
            )

            MyPageMenuItem(
                text = stringResource(R.string.my_page_terms_policies)
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 12.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(KeeplyTheme.colors.neutralWhite)
                .padding(16.dp)
        ) {
            KeeplyText(
                text = stringResource(R.string.my_page_other),
                style = KeeplyTheme.typography.subtitle03,
                color = KeeplyTheme.colors.neutral700
            )

            MyPageMenuItem(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .clickable { onLogoutClick() },
                text = stringResource(R.string.my_page_logout),
                iconVisibility = false
            )

            MyPageMenuItem(
                text = stringResource(R.string.my_page_withdrawal),
                iconVisibility = false
            )
        }
    }
}

@Composable
fun MyPageMenuItem(
    text: String,
    modifier: Modifier = Modifier,
    iconVisibility: Boolean = true,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        KeeplyText(
            modifier = Modifier
                .weight(1f),
            text = text,
            style = KeeplyTheme.typography.caption01
        )

        if (iconVisibility) {
            Icon(
                modifier = Modifier
                    .size(16.dp),
                painter = KeeplyTheme.icons.chevronRight,
                contentDescription = null,
                tint = KeeplyTheme.colors.neutral500
            )
        }
    }
}

@Preview
@Composable
fun MyRoutePreview() {
    KeeplyTheme {
        MyScreen()
    }
}