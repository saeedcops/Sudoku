package com.cops.sudoku.data.repository

import com.cops.sudoku.data.data_source.SettingDoa
import com.cops.sudoku.domain.model.Setting
import com.cops.sudoku.domain.repository.ISettingRepository

class SettingRepositoryImpl(val settingDoa: SettingDoa): ISettingRepository {
    override suspend fun insertSetting(setting: Setting) {
        settingDoa.updateSetting(setting)
    }

    override suspend fun getSetting(): Setting? {
        return settingDoa.getSetting()
    }


}