package com.cops.sudoku.domain.repository
import com.cops.sudoku.domain.model.Game
import com.cops.sudoku.domain.model.Statistic
import com.cops.sudoku.domain.util.Difficulty
import com.cops.sudoku.domain.util.SudokuNode
import com.cops.sudoku.domain.util.SudokuPuzzle
import kotlinx.coroutines.flow.Flow
import java.util.LinkedHashMap

interface IStatisticRepository {

    suspend fun insertStatistic(statistic: Statistic)
    fun getStatistics(difficulty: Difficulty): Flow<List<Statistic>>
}