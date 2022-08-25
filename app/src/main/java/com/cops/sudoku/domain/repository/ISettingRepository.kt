package com.cops.sudoku.domain.repository
import com.cops.sudoku.domain.model.Game
import com.cops.sudoku.domain.model.Setting
import com.cops.sudoku.domain.model.Statistic
import com.cops.sudoku.domain.util.Difficulty
import com.cops.sudoku.domain.util.SudokuNode
import com.cops.sudoku.domain.util.SudokuPuzzle
import kotlinx.coroutines.flow.Flow
import java.util.LinkedHashMap

interface ISettingRepository {

    suspend fun insertSetting(Setting: Setting)
    suspend fun getSetting(): Setting?
}