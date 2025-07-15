package com.keeply.presentation.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.keeply.presentation.R

@Immutable
data class KeeplyIconography(
    val add: Painter,
    val alarmStateOff: Painter,
    val alarmStateOn: Painter,
    val alignBox: Painter,
    val alignBoxBottomCenter: Painter,
    val arrowRight: Painter,
    val caretDown: Painter,
    val checkmark: Painter,
    val chevronDown: Painter,
    val chevronLeft: Painter,
    val chevronRight: Painter,
    val close: Painter,
    val closeFilled: Painter,
    val crop: Painter,
    val edit: Painter,
    val error: Painter,
    val folder: Painter,
    val folderFilled: Painter,
    val folderMoveTo: Painter,
    val home: Painter,
    val locked: Painter,
    val menuVertical: Painter,
    val scanAlt: Painter,
    val settings: Painter,
    val settingsFilled: Painter,
    val skip: Painter,
    val timer: Painter,
    val trashcan: Painter,
    val user: Painter,
    val warningFilled: Painter
)

@Composable
fun keeplyIconography() = KeeplyIconography(
    add = painterResource(id = R.drawable.ic_add),
    alarmStateOff = painterResource(id = R.drawable.ic_alarm_state_off),
    alarmStateOn = painterResource(id = R.drawable.ic_alarm_state_on),
    alignBox = painterResource(id = R.drawable.ic_align_box),
    alignBoxBottomCenter = painterResource(id = R.drawable.ic_align_box__bottom_center),
    arrowRight = painterResource(id = R.drawable.ic_arrow_right),
    caretDown = painterResource(id = R.drawable.ic_caret__down),
    checkmark = painterResource(id = R.drawable.ic_checkmark),
    chevronDown = painterResource(id = R.drawable.ic_chevron__down),
    chevronLeft = painterResource(id = R.drawable.ic_chevron_left),
    chevronRight = painterResource(id = R.drawable.ic_chevron_right),
    close = painterResource(id = R.drawable.ic_close),
    closeFilled = painterResource(id = R.drawable.ic_close_filled),
    crop = painterResource(id = R.drawable.ic_crop),
    edit = painterResource(id = R.drawable.ic_edit),
    error = painterResource(id = R.drawable.ic_error),
    folder = painterResource(id = R.drawable.ic_folder),
    folderFilled = painterResource(id = R.drawable.ic_folder_filled),
    folderMoveTo = painterResource(id = R.drawable.ic_folder_move_to),
    home = painterResource(id = R.drawable.ic_home),
    locked = painterResource(id = R.drawable.ic_locked),
    menuVertical = painterResource(id = R.drawable.ic_menu__vertical),
    scanAlt = painterResource(id = R.drawable.ic_scan_alt),
    settings = painterResource(id = R.drawable.ic_settings),
    settingsFilled = painterResource(id = R.drawable.ic_settings_filled),
    skip = painterResource(id = R.drawable.ic_skip),
    timer = painterResource(id = R.drawable.ic_timer),
    trashcan = painterResource(id = R.drawable.ic_trashcan),
    user = painterResource(id = R.drawable.ic_user),
    warningFilled = painterResource(id = R.drawable.ic_warning_filled)
)


val LocalIconography = staticCompositionLocalOf<KeeplyIconography> {
    error("No KeeplyIconography provided")
}