package com.cops.sudoku.presentation.setting

sealed class SettingEvent {
    object Theme : SettingEvent()
    object Sound : SettingEvent()
    object Vibration : SettingEvent()
}
