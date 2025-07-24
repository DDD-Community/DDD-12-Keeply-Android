package com.keeply.presentation.ui.splash

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.R
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.extend.getAppVersion
import com.keeply.presentation.extend.shadow03

@Composable
fun SplashScreen() {
    val context = LocalContext.current
    val view = LocalView.current
    val backgroundColor = KeeplyTheme.colors.neutral100

    DisposableEffect(backgroundColor) {
        val activity = view.context as? ComponentActivity
        activity?.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                backgroundColor.toArgb(),
                backgroundColor.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.light(
                backgroundColor.toArgb(),
                backgroundColor.toArgb()
            )
        )

        onDispose {}
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                enabled = false,
                onClick = {}
            )
            .background(KeeplyTheme.colors.neutral100)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .shadow03()
                    .background(
                        color = KeeplyTheme.colors.neutralWhite,
                    )
                    .padding(6.dp),
                painter = painterResource(R.drawable.ic_keeply_splash_logo),
                contentDescription = null
            )

            Icon(
                modifier = Modifier
                    .width(130.dp),
                painter = painterResource(R.drawable.ic_keeply_text_logo),
                contentDescription = null
            )
        }

        KeeplyText(
            modifier = Modifier
                .padding(bottom = 60.dp)
                .align(Alignment.BottomCenter),
            text = "v ${context.getAppVersion()}",
            style = KeeplyTheme.typography.caption01,
            color = KeeplyTheme.colors.neutral600
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    KeeplyTheme {
        SplashScreen()
    }
}