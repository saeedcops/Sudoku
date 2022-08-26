package com.cops.sudoku.data.data_source

import androidx.room.*
import com.cops.sudoku.domain.model.Setting

@Dao
interface SettingDoa {

    @Query("SELECT * FROM setting where id = 1")
    suspend fun getSetting(): Setting?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSetting(setting: Setting)


}