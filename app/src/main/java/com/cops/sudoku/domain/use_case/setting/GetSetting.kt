package com.cops.sudoku.domain.use_case.setting

import com.cops.sudoku.domain.model.Setting
import com.cops.sudoku.domain.repository.ISettingRepository

class GetSetting(private val setting:ISettingRepository) {

    suspend operator fun invoke(): Setting? {
        return setting.getSetting()
    }
}