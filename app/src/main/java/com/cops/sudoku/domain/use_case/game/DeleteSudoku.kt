package com.cops.sudoku.domain.use_case.game

import com.cops.sudoku.domain.model.Game
import com.cops.sudoku.domain.repository.IGameRepository
import com.cops.sudoku.domain.util.Difficulty
import com.cops.sudoku.domain.util.SudokuPuzzle
import kotlinx.coroutines.flow.Flow

class DeleteSudoku(private val gameRepository: IGameRepository) {

    suspend operator fun invoke(difficulty: Difficulty) {
        return gameRepository.deleteSudokuPuzzle(difficulty)
    }
}