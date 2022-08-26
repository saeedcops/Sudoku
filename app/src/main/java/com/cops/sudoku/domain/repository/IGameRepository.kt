package com.cops.sudoku.domain.repository
import com.cops.sudoku.domain.model.Game
import com.cops.sudoku.domain.util.Difficulty
import com.cops.sudoku.domain.util.SudokuPuzzle

interface IGameRepository {

    suspend fun createSudokuPuzzle(difficulty: Difficulty): SudokuPuzzle
    suspend  fun saveSudokuPuzzle(sudokuPuzzle: Game)
    suspend  fun deleteSudokuPuzzle(difficulty: Difficulty)
    suspend fun getSudokuPuzzle(difficulty: Difficulty): Game?
}