package com.keeply.presentation.ui.onboarding.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.R
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun KakaoLoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = Color(0xFFFFEB00)
        ),
        onClick = { /* 카카오 로그인 구현 */ }
    ) {
        Icon(
            modifier = Modifier
                .width(22.dp)
                .height(20.dp),
            painter = painterResource(R.drawable.ic_home),
            contentDescription = stringResource(R.string.kakao_login),
            tint = Color(0xFF3C1E1E)
        )

        KeeplyText(
            modifier = Modifier
                .padding(start = 16.dp),
            text = stringResource(R.string.kakao_login),
            style = KeeplyTheme.typography.button02Suit,
            color = Color(0xFF3C1E1E)
        )
    }
}

@Preview
@Composable
fun KakaoLoginButtonPreview() {
    KeeplyTheme {
        KakaoLoginButton()
    }
}