package com.cops.sudoku.presentation.game

sealed class UIEvent{
    object LOADING: UIEvent()
    object ACTIVE: UIEvent()
    object COMPLETE: UIEvent()
    object Mistake: UIEvent()
    object Hint: UIEvent()
    object Undo: UIEvent()
}
