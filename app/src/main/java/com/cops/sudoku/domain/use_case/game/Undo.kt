package com.cops.sudoku.domain.use_case.game

import com.cops.sudoku.domain.util.SudokuPuzzle

class Undo {

    operator fun invoke(
        sudokuPuzzle: SudokuPuzzle,
        onSuccess: () -> Unit
    ): SudokuPuzzle {
        sudokuPuzzle.graph.undo(onSuccess)

        return  sudokuPuzzle
    }
}