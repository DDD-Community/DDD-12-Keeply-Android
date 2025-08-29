package com.keeply.presentation.ui.folder.screenshot

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.keeply.presentation.R
import com.keeply.presentation.core.components.FileTag
import com.keeply.presentation.core.components.FileTagStyle
import com.keeply.presentation.core.components.ImageFrame
import com.keeply.presentation.core.components.InsightTextField
import com.keeply.presentation.core.components.KeeplyAppBar
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.components.colorBar.fromHexString
import com.keeply.presentation.core.components.colorBar.toComposeColor
import com.keeply.presentation.core.theme.KeeplyTheme
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun DetailScreenshotRoute(
    onBack: () -> Unit,
    viewModel: DetailScreenshotViewModel = hiltViewModel()
) {
    val state by viewModel.collectAsState()
    val context = LocalContext.current

    BackHandler { onBack.invoke() }

    DetailScreenshotScreen(
        presignedUrl = state.presignedUrl,
        folderName = state.folderName,
        folderColor = state.folderColor,
        textField = state.textField,
        textFieldLength = state.textFieldLength,
        textFieldMaxLength = state.textFieldMaxLength,
        onBack = onBack,
        onTextChange = viewModel::onTextChange
    )

}

@Composable
fun DetailScreenshotScreen(
    presignedUrl: String,
    folderName: String,
    folderColor: String,
    textField: String,
    textFieldLength: Int,
    textFieldMaxLength: Int,
    onBack: () -> Unit,
    onTextChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(KeeplyTheme.colors.neutral100)
    ) {
        KeeplyAppBar(
            leadingIcon = {
                Icon(
                    painter = KeeplyTheme.icons.chevronLeft,
                    contentDescription = "Back",
                    tint = KeeplyTheme.colors.neutralBlack
                )
            },
            trailingIcon = {
                Icon(
                    painter = KeeplyTheme.icons.menuVertical,
                    contentDescription = "menu",
                    tint = KeeplyTheme.colors.neutralBlack
                )
            },
            onClickLeading = onBack,
            onClickTrailing = { /*TODO: 메뉴*/ }
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .fillMaxWidth()
                ) {
                    FileTag(
                        modifier = Modifier
                            .weight(1f),
                        text = folderName,
                        style = FileTagStyle.Medium,
                        color = if (folderColor.isNotEmpty()) {
                            fromHexString(folderColor)?.toComposeColor()
                                ?: KeeplyTheme.colors.orange400
                        } else {
                            KeeplyTheme.colors.orange400
                        }
                    )

                    KeeplyText(
                        text = "2025.06.18",
                        style = KeeplyTheme.typography.subtitle03,
                        color = KeeplyTheme.colors.neutral600
                    )
                }
            }
            item {
                val painter = if (LocalInspectionMode.current) {
                    painterResource(id = R.drawable.img_onboarding_02)
                } else {
                    rememberAsyncImagePainter(model = presignedUrl)
                }

                ImageFrame(
                    painter = painter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 18.dp),
                )
            }

            item {
                KeeplyText(
                    text = stringResource(R.string.scan_insight_title),
                    style = KeeplyTheme.typography.header04,
                )

                InsightTextField(
                    value = textField,
                    onValueChange = onTextChange,
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 40.dp),
                    placeholder = stringResource(R.string.scan_insight_text_field),
                    maxLength = textFieldMaxLength
                )
            }


            item {
                Spacer(modifier = Modifier.height(9.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailScreenshotScreenPreview() {
    KeeplyTheme {
        DetailScreenshotScreen(
            presignedUrl = "",
            folderName = "폴더이름",
            folderColor = "",
            textField = "",
            textFieldLength = 0,
            textFieldMaxLength = 300,
            onTextChange = { },
            onBack = { }
        )
    }
}