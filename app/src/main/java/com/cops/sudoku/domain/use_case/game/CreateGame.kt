package com.cops.sudoku.domain.use_case.game

import com.cops.sudoku.domain.util.Difficulty
import com.cops.sudoku.domain.util.SudokuPuzzle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateGame {

    suspend operator fun invoke(difficulty: Difficulty): SudokuPuzzle = withContext(Dispatchers.IO) {
        SudokuPuzzle(difficulty= difficulty).setValue()
    }
}