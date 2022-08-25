package com.cops.sudoku.domain.util

import java.io.Serializable


data class SudokuNode(
    val x: Int,
    val y: Int,
    var answer: Int = 0,
    var correct: Int = 0,
    var readOnly: Boolean = false,
    var wrong: Boolean = false,
    var sameNumber: Boolean = false,
    var selected: Boolean = false,
    var shadow: Boolean = false,
    var pencilList:MutableSet<Int> = hashSetOf(),
): Serializable{
    /**
     * The x value is *100 to allow for uniqueness
     */
    override fun hashCode(): Int {
        return getHash(x, y)
    }
}

internal fun getHash(x: Int, y: Int):Int {
    val newX = x*100
    return "$newX$y".toInt()
}
