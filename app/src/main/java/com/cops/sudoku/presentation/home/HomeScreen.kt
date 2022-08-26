package com.cops.sudoku.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cops.sudoku.R
import com.cops.sudoku.domain.util.Difficulty
import com.cops.sudoku.domain.util.toLocalizedResource
import com.cops.sudoku.presentation.util.Screen
import com.cops.sudoku.ui.theme.ButtonBlue
import com.cops.sudoku.ui.theme.SudokuTheme
import com.cops.sudoku.ui.theme.ThemeState

@Composable
fun HomeScreen(navController: NavController,
               viewModel: HomeViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val difficulty:List<Difficulty> = listOf(Difficulty.EASY,Difficulty.MEDIUM,Difficulty.HARD,Difficulty.EXPERT)
    var dif by remember { mutableStateOf(1) }

    /*
   * This Box is to set background image to scaffold
   * */
    Box {

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = if(ThemeState.darkModeState.value)painterResource(R.drawable.bgdark)
            else painterResource(R.drawable.bgl1),
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds
        )
    SudokuTheme(ThemeState.darkModeState.value) {

        Scaffold(
            backgroundColor = Color.Transparent,
            scaffoldState = scaffoldState,
            topBar = {
                //This Row acting as TopAppBar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = "Sudoku",
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Cursive,
                        style = MaterialTheme.typography.h4

                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            viewModel.gameState.value=""
                            navController.navigate(
                                Screen.StatisticScreen.route
                            )
                        }) {
                            Icon(
                                imageVector = Icons.Default.QueryStats,
                                contentDescription = "statistic"
                            )
                        }

                        IconButton(onClick = {
                            viewModel.gameState.value=""
                            navController.navigate(
                                Screen.SettingScreen.route
                            )
                        }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Setting"
                            )
                        }

                    }
                }
            }

        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                //This Row for select difficulty
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        if (dif > 0) {
                            dif--
                            viewModel.onEvent(difficulty[dif])
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIos,
                            contentDescription = "down"
                        )
                    }


                    Text(text = difficulty[dif].toLocalizedResource, fontWeight = FontWeight.Bold)

                    IconButton(onClick = {
                        if (dif < 3) {
                            dif++
                            viewModel.onEvent(difficulty[dif])
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "up"
                        )
                    }
                }


                // New game Button
                Button(modifier = Modifier.wrapContentSize(),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        backgroundColor = ButtonBlue
                    ),
                    onClick = {
                    viewModel.gameState.value = ""
                    navController.navigate(
                        Screen.GameScreen.route +
                                "?diff=${dif}"
                    )
                }) {
                    Text(text = "New Game", fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(10.dp))

                // continue Button visible when there is saved game with the same difficulty
                AnimatedVisibility(
                        modifier = Modifier.fillMaxWidth(),
                        visible = viewModel.gameState.value != ""
                    ) {
                        Button(modifier = Modifier.wrapContentSize(),
                            colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            backgroundColor = ButtonBlue
                        ), onClick = {
                            viewModel.gameState.value = ""
                            navController.navigate(
                                Screen.GameScreen.route +
                                        "?diff=${dif - 4}"
                            )
                        }) {

                            Text(text = viewModel.gameState.value, fontSize = 16.sp)
                        }
                    }
            }


        }
    }
        }
}