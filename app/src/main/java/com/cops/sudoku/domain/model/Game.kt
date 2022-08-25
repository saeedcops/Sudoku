package com.cops.sudoku.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.cops.sudoku.domain.util.Difficulty
import com.cops.sudoku.domain.util.LastState
import com.cops.sudoku.domain.util.SudokuNode
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


@Entity
data class Game(
    @PrimaryKey
    val difficulty: Difficulty,
    val elapsedTime: Long,
    val mistake: Int,
    val hint: Int,
    @TypeConverters(BoardConverter::class)
    val board:LinkedHashMap<Int, SudokuNode>,
    @TypeConverters(ListConverter::class)
    val numberList: List<Int>,
    @TypeConverters(StackConverter::class)
    val stack: Stack<LastState>
    )


object BoardConverter {

    @TypeConverter
    @JvmStatic
    fun stringToMap(value: String): LinkedHashMap<Int, SudokuNode> {
        return Gson().fromJson(value,  object : TypeToken<LinkedHashMap<Int, SudokuNode>>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun mapToString(value: LinkedHashMap<Int, SudokuNode>?): String {
        return if(value == null) "" else Gson().toJson(value)
    }
}

object StackConverter {

    @TypeConverter
    @JvmStatic
    fun stringToStack(value: String): Stack<LastState> {
        return Gson().fromJson(value,  object : TypeToken<Stack<LastState>>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun stackToString(value: Stack<LastState>?): String {
        return if(value == null) "" else Gson().toJson(value)
    }
}

object ListConverter {

    @TypeConverter
    @JvmStatic
    fun stringToList(value: String): List<Int> {
        return Gson().fromJson(value,  object : TypeToken<List<Int>>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun listToString(value: List<Int>?): String {
        return if(value == null) "" else Gson().toJson(value)
    }
}
