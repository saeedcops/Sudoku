package com.cops.sudoku.presentation.boarding

import androidx.annotation.DrawableRes
import com.cops.sudoku.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPage(
        image = R.drawable.play,
        title = "How to Play",
        description = "Sudoku is played on a grid of 9 x 9 spaces.\n" +
                " Within the rows and columns are 9 “squares” (made up of 3 x 3 spaces).\n" +
                " Each row, column and square (9 spaces each) needs to be filled out with the numbers 1-9,\n" +
                " without repeating any numbers within the row, column or square."
    )

    object Second : OnBoardingPage(
        image = R.drawable.pencilplay,
        title = "How to use pencil",
        description = "Turn on Pencil mode to add and remove notes in a cell."
    )

    object Third : OnBoardingPage(
        image = R.drawable.fastplay,
        title = "How to play fast",
        description = "Turn on Fast mode to solve by touching a cell in the grid and must select the number first."
    )
}
