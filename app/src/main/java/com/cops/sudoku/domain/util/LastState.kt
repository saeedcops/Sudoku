package com.cops.sudoku.domain.util

data class LastState(val sudokuNode: SudokuNode,val numberList: List<Int> =  mutableListOf<Int>())
