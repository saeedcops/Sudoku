package com.cops.sudoku.domain.use_case.game

import com.cops.sudoku.domain.util.*

class SelectNode {

    operator fun invoke(
        x: Int,
        y: Int,
        lastX: Int =-1,
        lastY: Int =-1,
        sudokuPuzzle: SudokuPuzzle,
        isComplete: () -> Unit,
        onMistake: () -> Unit,
        isWrong: (Boolean) -> Unit
    ):SudokuPuzzle{

        sudokuPuzzle.graph.selectNode(x,y)

        // if fast mode answer when node selected
        if(sudokuPuzzle.fast){
            sudokuPuzzle.mistake += sudokuPuzzle.graph.updateNode(sudokuPuzzle.selectedNumber,isWrong)
            // check if complete
            if(sudokuPuzzle.graph.isCompleted())
                isComplete()

            if(sudokuPuzzle.mistake > 3 ){
                sudokuPuzzle.mistake = 3
                onMistake()
            }

            if (sudokuPuzzle.graph.getNode(x,y).readOnly
                ||sudokuPuzzle.graph.getNode(x,y).answer == sudokuPuzzle.graph.getNode(x,y).correct) {
                sudokuPuzzle.graph.getSameNumbers(sudokuPuzzle.selectedNumber,false)
                sudokuPuzzle.selectedNumber = sudokuPuzzle.graph.getNode(x, y).correct
            }

        // select node and set shadow
        }else{
            if(lastX != -1)
                sudokuPuzzle.graph.setShadow(lastX,lastY,false)

            sudokuPuzzle.graph.setShadow(x,y,true)
        }

        // remove shadow from last selected nodes
        if(lastX != -1)
            sudokuPuzzle.graph.getSameNumbers(lastX,lastY,false)

        // set shadow to the same nodes have the the number
        sudokuPuzzle.graph.getSameNumbers(x,y,true)

        return sudokuPuzzle
    }

}