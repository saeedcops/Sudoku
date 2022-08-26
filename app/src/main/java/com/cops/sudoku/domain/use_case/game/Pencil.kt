package com.cops.sudoku.domain.use_case.game

import com.cops.sudoku.domain.util.SudokuPuzzle

class Pencil {

    operator fun invoke(
        sudokuPuzzle: SudokuPuzzle
    ): SudokuPuzzle {

        sudokuPuzzle.pencil = !sudokuPuzzle.pencil
        if (sudokuPuzzle.pencil)
            sudokuPuzzle.fast=false
        return  sudokuPuzzle
    }
}