package com.cops.sudoku.domain.repository
import com.cops.sudoku.domain.model.Setting

interface ISettingRepository {

    suspend fun insertSetting(Setting: Setting)
    suspend fun getSetting(): Setting?
}