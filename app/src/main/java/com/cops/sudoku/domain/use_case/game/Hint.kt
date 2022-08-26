package com.cops.sudoku.domain.use_case.game

import com.cops.sudoku.domain.util.SudokuPuzzle

class Hint {

    operator fun invoke(
        sudokuPuzzle: SudokuPuzzle,
        onHintUp: () -> Unit,
        isComplete: () -> Unit,
        reward:Boolean
    ): SudokuPuzzle {

        if (sudokuPuzzle.hint >= 1 || reward){
            sudokuPuzzle.hint -= sudokuPuzzle.graph.hint()
            if (reward)
                sudokuPuzzle.hint=0
        }else{
            onHintUp()
        }

        if(sudokuPuzzle.graph.isCompleted())
            isComplete()

        return  sudokuPuzzle
    }
}