package com.cops.sudoku.domain.use_case.setting

import com.cops.sudoku.domain.model.Game
import com.cops.sudoku.domain.model.Setting
import com.cops.sudoku.domain.model.Statistic
import com.cops.sudoku.domain.repository.IGameRepository
import com.cops.sudoku.domain.repository.ISettingRepository
import com.cops.sudoku.domain.repository.IStatisticRepository
import com.cops.sudoku.domain.util.SudokuPuzzle
import kotlinx.coroutines.flow.Flow

class InsertSetting(private val isetting: ISettingRepository) {

    suspend operator fun invoke(setting: Setting) {
        return isetting.insertSetting(setting)
    }
}