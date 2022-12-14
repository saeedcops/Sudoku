package com.cops.sudoku.domain.use_case.game

import com.cops.sudoku.domain.model.Game
import com.cops.sudoku.domain.repository.IGameRepository
import com.cops.sudoku.domain.util.Difficulty

class GetSudoku(private val gameRepository: IGameRepository) {

    suspend operator fun invoke(difficulty: Difficulty):Game? {
        return gameRepository.getSudokuPuzzle(difficulty)
    }
}