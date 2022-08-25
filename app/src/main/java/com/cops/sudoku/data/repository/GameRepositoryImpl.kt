package com.cops.sudoku.data.repository

import com.cops.sudoku.data.data_source.SudokuDoa
import com.cops.sudoku.domain.model.Game
import com.cops.sudoku.domain.repository.IGameRepository
import com.cops.sudoku.domain.util.Difficulty
import com.cops.sudoku.domain.util.SudokuPuzzle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameRepositoryImpl(val sudokuDoa: SudokuDoa): IGameRepository {


    override suspend fun createSudokuPuzzle(difficulty: Difficulty): SudokuPuzzle =
        withContext(Dispatchers.IO) {
         SudokuPuzzle(difficulty= difficulty)
    }

    override suspend fun saveSudokuPuzzle(sudokuPuzzle: Game) {
        sudokuDoa.updateSudoku(sudokuPuzzle)
    }

    override suspend fun deleteSudokuPuzzle(difficulty: Difficulty) {
        sudokuDoa.deleteGame(difficulty)
    }

    override suspend fun getSudokuPuzzle(difficulty: Difficulty): Game? {
        return sudokuDoa.getSudoku(difficulty)
    }

}