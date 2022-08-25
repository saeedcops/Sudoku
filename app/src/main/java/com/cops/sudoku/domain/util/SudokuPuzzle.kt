package com.cops.sudoku.domain.util

import java.io.Serializable


data class SudokuPuzzle(
    var reload: Boolean=false,
    var isReady: Boolean=false,
    var fast: Boolean=false,
    var pencil: Boolean=false,
    var mistake: Int =0,
    var hint: Int =3,
    var selectedNumber: Int =-1,
    var difficulty: Difficulty = Difficulty.MEDIUM,
    var graph:SudokuBoard = SudokuBoard(),
    var elapsedTime: Long = 0L
): Serializable {

   fun setValue(): SudokuPuzzle{

       this.graph.setDifficulty(difficulty)
       this.isReady=true
       this.graph.build()

       return this
    }
    fun getValue(): SudokuBoard = graph
}
