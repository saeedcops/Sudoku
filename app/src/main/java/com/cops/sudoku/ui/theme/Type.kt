package com.cops.sudoku.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),

    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)


val inputButton = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Black,
    fontSize = 28.sp,
    textAlign = TextAlign.Center
)

fun readOnlySudokuSquare(tileOffset: Float,dark:Boolean) :TextStyle {
    var color = Color.Black
    if(dark)
        color = White
    return TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = (tileOffset * .75).sp,
            textAlign = TextAlign.Center,
            color = color
    )
}

fun mutableSudokuSquare(tileOffset: Float) = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    fontSize = (tileOffset * .75).sp,
    textAlign = TextAlign.Center,
    color = ButtonBlue
)

fun mutableSudokuSquareWrong(tileOffset: Float) = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    fontSize = (tileOffset * .75).sp,
    textAlign = TextAlign.Center,
    color = LightRed
)