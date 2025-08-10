package com.keeply.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.theme.KeeplyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KeeplyModalBottomSheet(
    onDismissRequest: () -> Unit = {},
    skipPartiallyExpanded: Boolean = true,
    content: @Composable () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        sheetState = sheetState,
        containerColor = KeeplyTheme.colors.neutralWhite,
        contentColor = KeeplyTheme.colors.neutralBlack,
        scrimColor = Color.Black.copy(alpha = 0.5f),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clip(CircleShape)
                    .width(60.dp)
                    .height(4.dp)
                    .background(KeeplyTheme.colors.neutral300)
            )
        }
    ) {
        content()
    }
}

@Preview
@Composable
private fun KeeplyModalBottomSheetPreview() {
    KeeplyTheme {
        // Preview doesn't work well with ModalBottomSheet
    }
}