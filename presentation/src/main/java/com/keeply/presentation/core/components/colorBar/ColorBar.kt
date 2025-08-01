package com.keeply.presentation.core.components.colorBar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.keeply.presentation.core.theme.KeeplyTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlin.Boolean

@Composable
fun ColorBar(
    modifier: Modifier = Modifier,
    selectedColor: FolderColor = FolderColor.ORANGE,
    colors: ImmutableList<FolderColor> = FolderColor.values().toList().toImmutableList(),
    onColorSelected: (FolderColor) -> Unit = {},
) {

    Row(
        modifier = modifier,
    ) {
        colors.forEach { folderColor ->
            ColorBarCheckBox(
                color = folderColor.toComposeColor(),
                checked = selectedColor == folderColor,
                onClick = {
                    onColorSelected(folderColor)
                }
            )
        }
    }
}

@Composable
private fun ColorBarCheckBox(
    modifier: Modifier = Modifier,
    color: Color,
    checked: Boolean,
    onClick: () -> Unit = {},
) {
    val checkedModifier = if (checked)
        Modifier.border(
            width = 1.dp,
            shape = RoundedCornerShape(4.dp),
            color = KeeplyTheme.colors.neutral200
        )
    else
        Modifier

    Box(
        modifier = modifier
            .triStateToggleable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                state = ToggleableState(checked),
                role = Role.Checkbox,
                onClick = onClick
            )
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .size(24.dp)
            .then(checkedModifier)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                modifier = Modifier
                    .size(16.dp),
                painter = KeeplyTheme.icons.checkmark,
                tint = KeeplyTheme.colors.neutralWhite,
                contentDescription = true.toString()
            )
        }
    }
}

@Preview
@Composable
fun ColorBarPreview() {
    KeeplyTheme {
        ColorBar()
    }
}