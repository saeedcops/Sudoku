package com.cops.sudoku.presentation

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.cops.sudoku.domain.model.Setting
import com.cops.sudoku.domain.use_case.setting.SettingUseCases
import com.cops.sudoku.presentation.util.*
import com.cops.sudoku.ui.theme.SudokuTheme
import com.cops.sudoku.ui.theme.ThemeState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var setting: Setting
    @Inject
    lateinit var settingUseCases: SettingUseCases

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContent {

            val coroutineScope = rememberCoroutineScope()
            coroutineScope.launch {
                settingUseCases.getSetting().let {
                    if (it != null)
                        setting=it
                    ThemeState.darkModeState.value= setting.dark
                }
            }
            SudokuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    NavGraph()
                }
            }
        }

    }
}
