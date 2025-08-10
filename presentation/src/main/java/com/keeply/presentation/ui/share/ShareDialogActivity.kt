package com.keeply.presentation.ui.share

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.lifecycle.lifecycleScope
import com.keeply.presentation.core.components.KeeplyToast
import com.keeply.presentation.core.components.ToastManager
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShareDialogActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 투명한 배경 설정
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.decorView.setBackgroundColor(android.graphics.Color.TRANSPARENT)

        val imageUri = when (intent.action) {
            Intent.ACTION_SEND -> {
                if (intent.type?.startsWith("image/") == true) {
                    intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
                } else null
            }

            else -> null
        }

        if (imageUri == null) {
            finish()
            return
        }

        setContent {
            // 투명한 배경 설정 - 테마 없이 직접 구성
            var showDialog by remember { mutableStateOf(true) }
            var isClosingForLater by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                if (showDialog) {
                    ShareOptionDialog(
                        onSaveNow = {
                            // 지금 저장하기 - MainActivity로 이동하여 ScanBeforeScreen으로 네비게이션
                            val mainIntent =
                                Intent(this@ShareDialogActivity, MainActivity::class.java).apply {
                                    action = Intent.ACTION_SEND
                                    type = "image/*"
                                    putExtra(Intent.EXTRA_STREAM, imageUri)
                                    putExtra("navigate_to", "scan_before")
                                    flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                                }
                            startActivity(mainIntent)
                            finish()
                        },
                        onSaveLater = {
                            // 다이얼로그 먼저 닫기
                            isClosingForLater = true
                            showDialog = false

                            // 나중에 저장하기 - 토스트 표시
                            ToastManager.show(
                                title = "미분류 이미지 저장 완료",
                                content = "7일 이내 분류하지 않으면 사라져요!"
                            )
                            // TODO: 이미지를 임시 저장소에 저장

                            // 토스트가 표시된 후 3초 뒤에 종료
                            lifecycleScope.launch {
                                delay(3000)
                                finish()
                            }
                        },
                        onDismiss = {
                            if (!isClosingForLater) {
                                finish()
                            }
                        }
                    )
                }

                // Global Toast Overlay
                ToastManager.toastState?.let { toastData ->
                    KeeplyTheme {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .zIndex(999f),
                            contentAlignment = toastData.contentAlignment
                        ) {
                            KeeplyToast(
                                title = toastData.title,
                                content = toastData.content,
                                isVisible = true,
                                onDismiss = { ToastManager.hide() },
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    // 뒤로가기 버튼 눌렀을 때
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

@Composable
private fun ShareOptionDialog(
    onSaveNow: () -> Unit,
    onSaveLater: () -> Unit,
    onDismiss: () -> Unit,
) {
    KeeplyTheme {
        Dialog(onDismissRequest = onDismiss) {
            Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = KeeplyTheme.colors.neutral100
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "이미지 저장",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = KeeplyTheme.colors.neutral900
                )

                Text(
                    text = "공유받은 이미지를 어떻게 저장할까요?",
                    fontSize = 14.sp,
                    color = KeeplyTheme.colors.neutral600,
                    modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onSaveLater
                    ) {
                        Text(
                            text = "나중에 저장하기",
                            color = KeeplyTheme.colors.neutralBlack
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(
                        onClick = onSaveNow
                    ) {
                        Text(
                            text = "지금 저장하기",
                            color = KeeplyTheme.colors.neutralBlack
                        )
                    }
                }
            }
        }
    }
    }
}