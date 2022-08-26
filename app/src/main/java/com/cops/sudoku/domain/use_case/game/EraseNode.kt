package com.cops.sudoku.domain.use_case.game

import com.cops.sudoku.domain.util.SudokuPuzzle

class EraseNode {

    operator fun invoke(
        sudokuPuzzle: SudokuPuzzle,
    ): SudokuPuzzle {
        sudokuPuzzle.graph.eraseNode()

        return  sudokuPuzzle
    }
}