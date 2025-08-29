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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.keeply.domain.image.usecase.SaveImageUseCase
import com.keeply.presentation.core.components.KeeplyAlertModal
import com.keeply.presentation.core.components.KeeplyToast
import com.keeply.presentation.core.components.ToastManager
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.ui.main.MainActivity
import com.keeply.presentation.util.toFile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class ShareDialogActivity : ComponentActivity() {

    @Inject
    lateinit var saveImageUseCase: SaveImageUseCase

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
                modifier = Modifier
                    .navigationBarsPadding()
                    .statusBarsPadding()
                    .fillMaxSize()
            ) {
                if (showDialog) {
                    KeeplyAlertModal(
                        title = "이미지 저장",
                        content = "해당 이미지를 어떻게 저장할까요?\n이미지만 저장 시 미분류로 저장돼요.",
                        confirmButtonText = "텍스트와 저장",
                        cancelButtonText = "이미지만 저장",
                        confirmButtonCallback = {
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
                        cancelButtonCallback = {
                            // 다이얼로그 먼저 닫기
                            isClosingForLater = true
                            showDialog = false

                            // 이미지를 파일로 변환하고 API 호출
                            lifecycleScope.launch {
                                try {
                                    // URI를 File로 변환

                                    val file = imageUri.toFile(this@ShareDialogActivity)

                                    // SaveImage API 호출
                                    saveImageUseCase(file).catch { error ->
                                        // 에러 발생 시 에러 토스트 표시
                                        ToastManager.show(
                                            title = "이미지 저장 실패",
                                            content = "다시 시도해 주세요."
                                        )
                                    }.collect { imageId ->
                                        // 성공 시 성공 토스트 표시
                                        ToastManager.show(
                                            title = "미분류 이미지 저장 완료",
                                            content = "7일 이내 분류하지 않으면 사라져요!"
                                        )
                                    }
                                } catch (e: Exception) {
                                    // 파일 변환 실패 시
                                    ToastManager.show(
                                        title = "이미지 저장 실패",
                                        content = "다시 시도해 주세요."
                                    )
                                }

                                // 토스트가 표시된 후 3초 뒤에 종료
                                delay(3000)
                                finish()
                            }
                        },
                        onDismissCallback = {
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