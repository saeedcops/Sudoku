package com.cops.sudoku.data.data_source

import androidx.room.*
import com.cops.sudoku.domain.model.Game
import com.cops.sudoku.domain.model.Statistic
import com.cops.sudoku.domain.util.Difficulty
import com.cops.sudoku.domain.util.SudokuPuzzle
import kotlinx.coroutines.flow.Flow

@Dao
interface StatisticsDoa {

    @Query("SELECT * FROM statistic where difficulty =:difficulty order by elapsedTime")
     fun getStatistic(difficulty: Difficulty): Flow<List<Statistic>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatistic(statistic: Statistic)


}