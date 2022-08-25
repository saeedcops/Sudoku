package com.cops.sudoku.presentation.setting

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cops.sudoku.domain.model.Setting
import com.cops.sudoku.domain.use_case.setting.SettingUseCases
import com.cops.sudoku.ui.theme.ThemeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingUseCases: SettingUseCases,
    val setting: Setting
) : ViewModel()  {

     val settingState = mutableStateOf<Setting>(setting)

    init {
        viewModelScope.launch {
            settingUseCases.getSetting().let {
                if (it != null) {
                    settingState.value = it
//                    ThemeState.darkModeState.value = settingState.value.dark
                }
            }
        }
    }

    fun onEvent(event: SettingEvent){

        when(event){
            SettingEvent.Sound -> {
                settingState.value=setting.copy(sound = !setting.sound)
                setting.sound = !setting.sound

            }
            SettingEvent.Theme -> {
                settingState.value=setting.copy(dark = !setting.dark)
                setting.dark = !setting.dark
                ThemeState.darkModeState.value=setting.dark
            }
            SettingEvent.Vibration -> {
                settingState.value=setting.copy(vibration = !setting.vibration)
                setting.vibration = !setting.vibration
            }
        }
//        settingState.value=setting.copy()
        viewModelScope.launch {
            settingUseCases.insertSetting(setting)
        }

    }

}