package com.cops.sudoku.presentation.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cops.sudoku.domain.use_case.setting.SettingUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val settingUseCases: SettingUseCases
) : ViewModel()  {

    var status =mutableStateOf(false)
    // to check if game opened before to show how to play onBoarding
    val isSaved = mutableStateOf(false)

    fun onEvent(){

        viewModelScope.launch {
            settingUseCases.getSetting().let {
                if (it != null && it.id == 1)
                    isSaved.value=true

                status.value=true
            }
        }
    }

}