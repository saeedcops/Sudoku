package com.cops.sudoku.domain.use_case.game

import com.cops.sudoku.domain.model.Game
import com.cops.sudoku.domain.repository.IGameRepository

class UpdateSudoku(private val gameRepository: IGameRepository) {

    suspend operator fun invoke(
        sudokuPuzzle: Game
    ) {
        gameRepository.saveSudokuPuzzle(sudokuPuzzle)
    }
}