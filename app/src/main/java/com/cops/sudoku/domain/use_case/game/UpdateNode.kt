package com.cops.sudoku.domain.use_case.game

import com.cops.sudoku.domain.util.SudokuPuzzle

class UpdateNode {
     operator fun invoke(
        value: Int,
        sudokuPuzzle: SudokuPuzzle,
        onMistake: () -> Unit,
        isComplete: () -> Unit,
        isWrong: (Boolean) -> Unit
    ):SudokuPuzzle{

         if(sudokuPuzzle.fast) {
             sudokuPuzzle.graph.getSameNumbers(sudokuPuzzle.selectedNumber,false)
             sudokuPuzzle.graph.getSameNumbers(value,true)

             sudokuPuzzle.selectedNumber = value

             return sudokuPuzzle
         }

         sudokuPuzzle.selectedNumber = value

         if (sudokuPuzzle.pencil){

             sudokuPuzzle.graph.updatePencil(value)

         }else {

             sudokuPuzzle.mistake += sudokuPuzzle.graph.updateNode(value,isWrong)
             if(sudokuPuzzle.graph.isCompleted())
                 isComplete()

            if(sudokuPuzzle.mistake >= 3 ){
                sudokuPuzzle.mistake = 3
                onMistake()
            }
         }

         return  sudokuPuzzle
    }
}