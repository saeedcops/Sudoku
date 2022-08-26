package com.cops.sudoku.domain.util

import kotlin.math.sqrt

internal val Int.sqrt: Int
    get() = sqrt(this.toDouble()).toInt()

internal fun Long.toTime(): String {
    if (this >= 3600) return "+59:59"
    var minutes = ((this % 3600) / 60).toString()
    if (minutes.length == 1) minutes = "0$minutes"
    var seconds = (this % 60).toString()
    if (seconds.length == 1) seconds = "0$seconds"


    return String.format("$minutes:$seconds")
}


internal val Difficulty.toLocalizedResource: String
    get() {
        return when (this) {
            Difficulty.EASY -> "EASY"
            Difficulty.MEDIUM -> "MEDIUM"
            Difficulty.HARD -> "HARD"
            Difficulty.EXPERT -> "EXPERT"
        }
    }
