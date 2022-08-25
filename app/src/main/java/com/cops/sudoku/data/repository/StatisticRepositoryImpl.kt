package com.cops.sudoku.data.repository

import com.cops.sudoku.data.data_source.StatisticsDoa
import com.cops.sudoku.domain.model.Statistic
import com.cops.sudoku.domain.repository.IStatisticRepository
import com.cops.sudoku.domain.util.Difficulty
import kotlinx.coroutines.flow.Flow

class StatisticRepositoryImpl(val statisticsDoa: StatisticsDoa): IStatisticRepository {
    override suspend fun insertStatistic(statistic: Statistic) {
        statisticsDoa.insertStatistic(statistic)
    }

    override fun getStatistics(difficulty: Difficulty): Flow<List<Statistic>> {
       return statisticsDoa.getStatistic(difficulty)
    }

}