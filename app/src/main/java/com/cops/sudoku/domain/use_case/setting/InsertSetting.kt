package com.cops.sudoku.domain.use_case.setting

import com.cops.sudoku.domain.model.Setting
import com.cops.sudoku.domain.repository.ISettingRepository

class InsertSetting(private val isetting: ISettingRepository) {

    suspend operator fun invoke(setting: Setting) {
        return isetting.insertSetting(setting)
    }
}