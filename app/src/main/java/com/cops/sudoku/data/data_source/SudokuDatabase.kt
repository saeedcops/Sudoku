package com.cops.sudoku.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cops.sudoku.domain.model.*

@Database(entities = [Game::class, Statistic::class, Setting::class], version = 6, exportSchema = false)
@TypeConverters( BoardConverter::class, ListConverter::class, StackConverter::class)
abstract class SudokuDatabase : RoomDatabase(){

    abstract val sudokuDoa:SudokuDoa
    abstract val statisticsDoa:StatisticsDoa
    abstract val settingDoa:SettingDoa

    companion object {
        const val DATABASE_NAME="sudoku_db"
    }
}