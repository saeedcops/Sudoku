package com.cops.sudoku.domain.use_case.game

import com.cops.sudoku.domain.util.SudokuPuzzle
import com.cops.sudoku.domain.util.getHash

class Fast {

    operator fun invoke(
        sudokuPuzzle: SudokuPuzzle
    ): SudokuPuzzle {

            sudokuPuzzle.graph.getSameNumbers(sudokuPuzzle.selectedNumber,false)
            sudokuPuzzle.graph.setShadow(false)

            sudokuPuzzle.fast = !sudokuPuzzle.fast
        if (sudokuPuzzle.fast)
            sudokuPuzzle.pencil=false

        return  sudokuPuzzle
    }
}