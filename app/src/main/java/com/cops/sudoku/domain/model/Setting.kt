package com.cops.sudoku.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Setting(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", defaultValue = "1")
    val id :Int=1,
    @ColumnInfo(name = "dark", defaultValue = true.toString())
    var dark :Boolean=true,
    @ColumnInfo(name = "sound", defaultValue = true.toString())
    var sound :Boolean=true,
    @ColumnInfo(name = "vibration", defaultValue = true.toString())
    var vibration :Boolean=true,
)
