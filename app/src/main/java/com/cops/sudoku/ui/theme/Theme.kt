package com.cops.sudoku.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*

private val DarkColorPalette = darkColors(
    primary = ButtonBlue,
    primaryVariant = ButtonBlue,
    secondary = ButtonBlue
)

private val LightColorPalette = lightColors(
    primary = ButtonBlue,
    primaryVariant = ButtonBlue,
    secondary = ButtonBlue,
)

object ThemeState {
    var darkModeState : MutableState<Boolean> = mutableStateOf(false)
}
@Composable
fun SudokuTheme(darkTheme: Boolean = ThemeState.darkModeState.value, content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}