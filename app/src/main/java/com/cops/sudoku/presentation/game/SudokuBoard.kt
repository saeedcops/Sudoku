package com.cops.sudoku.presentation.game

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cops.sudoku.domain.util.getHash
import com.cops.sudoku.domain.util.sqrt
import com.cops.sudoku.ui.theme.*


@Composable
fun InputButtonRow(
    viewModel: GameViewModel
) {
    LazyRow(){

        itemsIndexed(viewModel.sudokuState.value.graph.generateNumberList()) { i, item ->
            val index = i + 1
            if (item > 0){

            Card(modifier = Modifier
                .clickable {
                    viewModel.onEvent(GameEvent.OnInput(index))
                }
                .border(1.dp, ButtonBlue, RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(8.dp),
                elevation = 2.dp) {

                Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = index.toString(),
                        style = inputButton.copy(
                            color = if (viewModel.sudokuState.value.pencil) Color.Gray
                            else if (viewModel.sudokuState.value.fast
                                && viewModel.sudokuState.value.selectedNumber == index
                            )
                                BlueViolet1
                            else ButtonBlue
                        ),
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(5.dp)
                    )

                    Text(
                        text = item.toString(),
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(bottom =  2.dp)
                    )
                }

            }
        }
            Spacer(modifier = Modifier.size(6.dp))
        }
    }


}


@Composable
fun SudokuTile(
    viewModel: GameViewModel,
    size: Dp
) {
    val boundary = 9
    val tileOffset = size.value / boundary
    SudokuTextFields(
        tileOffset = tileOffset,
        viewModel = viewModel
    )
    //draw lines
    BoardGrid(boundary = boundary, tileOffset = tileOffset)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SudokuTextFields(
    tileOffset: Float,
    viewModel: GameViewModel,
) {
        for (r in 0..8) {
            for (c in 0..8) {
                val node = viewModel.sudokuState.value.graph.generateBoard().get(getHash(r, c))

                node?.answer!!
                if ( !node.readOnly && node.answer == 0) {

                    LazyVerticalGrid(modifier = Modifier
                        .absoluteOffset(
                            (tileOffset * (r)).dp,
                            (tileOffset * (c)).dp
                        )
                        .background(
                            if (node.selected == true) BlueViolet1
                            else if (node.shadow == true) {
                                if (ThemeState.darkModeState.value) DeepBlue
                                else TextWhite
                            } else Color.Transparent
                        )
                        .width(tileOffset.dp)
                        .height(tileOffset.dp)

                        .clickable {
                            viewModel.onEvent(GameEvent.OnTileFocused(r, c))
                        },
                        cells = GridCells.Fixed(2), contentPadding = PaddingValues(2.dp)){
                        items(node.pencilList.toList()){
                            Text( text = it.toString(),
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold, fontSize = 12.sp
                            )
                        }

                    }

                } else {

                Text(text = if (node.readOnly == true) node.correct.toString()
                            else if (node.answer > 0) node.answer.toString() else "",

                    style = if (node.readOnly == true) readOnlySudokuSquare(
                        tileOffset,
                        ThemeState.darkModeState.value
                    )
                    else if (!node.wrong) mutableSudokuSquare(tileOffset)
                    else mutableSudokuSquareWrong(tileOffset),
                    modifier = Modifier
                        .absoluteOffset(
                            (tileOffset * (r)).dp,
                            (tileOffset * (c)).dp
                        )
                        .background(
                            if (node.selected == true) BlueViolet1
                            else if (node.shadow == true) {
                                if (ThemeState.darkModeState.value) DeepBlue
                                else TextWhite
                            } else Color.Transparent
                        )
                        .width(tileOffset.dp)
                        .height(tileOffset.dp)
                        .clickable {
                            viewModel.onEvent(GameEvent.OnTileFocused(r, c))
                        })
                 }
            }

        }

}


@Composable
fun BoardGrid(boundary: Int, tileOffset: Float) {
    (1 until boundary).forEach {
        val width = if (it % boundary.sqrt == 0) 3.dp
        else 1.dp
        Divider(
            color = BlueViolet3,
            modifier = Modifier
                .absoluteOffset((tileOffset * it).dp, 0.dp)
                .fillMaxHeight()
                .width(width)
        )

        val height = if (it % boundary.sqrt == 0) 3.dp
        else 1.dp
        Divider(
            color = BlueViolet3,
            modifier = Modifier
                .absoluteOffset(0.dp, (tileOffset * it).dp)
                .fillMaxWidth()
                .height(height)
        )
    }
}
