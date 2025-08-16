package com.keeply.presentation.core.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.R
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun KeeplyToggle(
    checked: Boolean,
    modifier: Modifier = Modifier,
    onClick: (Boolean) -> Unit = {},
) {
    Row(
        modifier = modifier
            .triStateToggleable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                state = ToggleableState(checked),
                role = Role.Checkbox,
                onClick = { onClick(checked.not()) }
            )
            .width(40.dp)
            .height(22.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            modifier = Modifier
                .width(40.dp)
                .height(22.dp),
            painter = painterResource(
                if (checked) R.drawable.ic_toggle_active
                else R.drawable.ic_toggle_inactive
            ),
            contentDescription = checked.toString(),
            tint = Unspecified
        )
    }
}

@Preview
@Composable
fun KeeplyTogglePreview() {
    KeeplyTheme {
        KeeplyToggle(
            checked = true
        )
    }
}