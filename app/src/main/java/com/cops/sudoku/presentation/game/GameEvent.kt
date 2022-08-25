package com.cops.sudoku.presentation.game

import com.cops.sudoku.domain.util.Difficulty

sealed class GameEvent {
    data class OnInput(val input: Int) : GameEvent()
    data class OnCreate(val difficulty: Difficulty =Difficulty.MEDIUM) : GameEvent()
    data class OnTileFocused(val x: Int, val y: Int) : GameEvent()
    data class Hint(val reward: Boolean) : GameEvent()
    object Theme : GameEvent()
    object Sound : GameEvent()
    object Vibration : GameEvent()
    object Fast : GameEvent()
    object Pencil : GameEvent()
    object Erase : GameEvent()
    object Undo : GameEvent()
    object OnRestart : GameEvent()
    object OnLoading : GameEvent()
    object OnStop : GameEvent()
    object OnContinue : GameEvent()
    object OnPlayAgain : GameEvent()
}
