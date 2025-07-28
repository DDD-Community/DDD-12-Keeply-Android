package com.keeply.presentation.core.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun KeeplyCheckBox(
    checked: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    onClick: (Boolean) -> Unit = {},
) {

    Row(
        modifier = modifier.triStateToggleable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            state = ToggleableState(checked),
            role = Role.Checkbox,
            onClick = { onClick(checked.not()) }
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(16.dp),
            painter = KeeplyTheme.icons.checkmark,
            contentDescription = text + checked.toString(),
            tint = if (checked) KeeplyTheme.colors.orange400 else KeeplyTheme.colors.neutral600
        )

        Text(
            text = text,
            style = KeeplyTheme.typography.body,
            color = if (checked) KeeplyTheme.colors.neutral900 else KeeplyTheme.colors.neutral600
        )
    }
}

@Preview(showBackground = true)
@Composable
fun KeeplyCheckBoxPreview() {
    KeeplyTheme {
        KeeplyCheckBox(
            checked = true,
            text = "Sample Checkbox"
        )
    }
}
