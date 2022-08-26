package com.cops.sudoku.domain.repository
import com.cops.sudoku.domain.model.Statistic
import com.cops.sudoku.domain.util.Difficulty
import kotlinx.coroutines.flow.Flow

interface IStatisticRepository {

    suspend fun insertStatistic(statistic: Statistic)
    fun getStatistics(difficulty: Difficulty): Flow<List<Statistic>>
}