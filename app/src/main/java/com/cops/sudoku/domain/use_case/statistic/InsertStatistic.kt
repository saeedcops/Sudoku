package com.cops.sudoku.domain.use_case.statistic

import com.cops.sudoku.domain.model.Game
import com.cops.sudoku.domain.model.Statistic
import com.cops.sudoku.domain.repository.IGameRepository
import com.cops.sudoku.domain.repository.IStatisticRepository
import com.cops.sudoku.domain.util.SudokuPuzzle
import kotlinx.coroutines.flow.Flow

class InsertStatistic(private val statistics: IStatisticRepository) {

    suspend operator fun invoke(statistic:Statistic) {
        return statistics.insertStatistic(statistic)
    }
}