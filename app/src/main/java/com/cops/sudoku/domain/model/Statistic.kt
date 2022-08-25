package com.cops.sudoku.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cops.sudoku.domain.util.Difficulty


@Entity
data class Statistic(
    @PrimaryKey
    val id: Int? = null,
    val difficulty: Difficulty,
    val elapsedTime: Long,
    val mistake: Int,
    val hint: Int,
    )
