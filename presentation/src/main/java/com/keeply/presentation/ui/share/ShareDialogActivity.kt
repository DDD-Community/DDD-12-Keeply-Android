package com.keeply.presentation.ui.share

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareDialogActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
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
            // 테마 없이 직접 다이얼로그만 표시
            ShareOptionDialog(
                    onSaveNow = {
                        // 지금 저장하기 - MainActivity로 이동하여 ScanBeforeScreen으로 네비게이션
                        val mainIntent = Intent(this, MainActivity::class.java).apply {
                            action = Intent.ACTION_SEND
                            type = "image/*"
                            putExtra(Intent.EXTRA_STREAM, imageUri)
                            putExtra("navigate_to", "scan_before")
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        }
                        startActivity(mainIntent)
                        finish()
                    },
                    onSaveLater = {
                        // 나중에 저장하기 - 그냥 종료
                        // TODO: 이미지를 임시 저장소에 저장
                        finish()
                    },
                    onDismiss = {
                        finish()
                    }
                )
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
    onDismiss: () -> Unit
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