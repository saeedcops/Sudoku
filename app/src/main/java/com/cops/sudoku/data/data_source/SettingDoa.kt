package com.cops.sudoku.data.data_source

import androidx.room.*
import com.cops.sudoku.domain.model.Game
import com.cops.sudoku.domain.model.Setting
import com.cops.sudoku.domain.model.Statistic
import com.cops.sudoku.domain.util.Difficulty
import com.cops.sudoku.domain.util.SudokuPuzzle
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingDoa {

    @Query("SELECT * FROM setting where id = 1")
    suspend fun getSetting(): Setting?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSetting(setting: Setting)


}