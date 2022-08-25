package com.cops.sudoku.presentation.game

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cops.sudoku.R
import com.cops.sudoku.domain.util.toLocalizedResource
import com.cops.sudoku.domain.util.toTime
import com.cops.sudoku.presentation.game.commponanet.HintDialog
import com.cops.sudoku.presentation.game.commponanet.PauseDialog
import com.cops.sudoku.presentation.game.commponanet.WinDialog
import com.cops.sudoku.presentation.game.commponanet.WrongDialog
import com.cops.sudoku.presentation.util.*
import com.cops.sudoku.ui.theme.BlueViolet1
import com.cops.sudoku.ui.theme.SudokuTheme
import com.cops.sudoku.ui.theme.ThemeState

import kotlinx.coroutines.flow.collectLatest

@Composable
fun GameScreen(navController: NavController,
               viewModel: GameViewModel = hiltViewModel()
) {
    val openWinDialog = remember { mutableStateOf(false) }
    val openPauseDialog = remember { mutableStateOf(false) }
    val openWrongDialog = remember { mutableStateOf(false) }
    val openHintDialog = remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val ctx= LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                UIEvent.ACTIVE -> Log.d("UIEvent", "ACTIVE")
                UIEvent.COMPLETE -> {
                    openWinDialog.value = true
                }
                UIEvent.LOADING -> Log.d("UIEvent", "LOADING")
                UIEvent.Hint -> {
                    openHintDialog.value = true
                }//{scaffoldState.snackbarHostState.showSnackbar("3 Hint")}
                UIEvent.Mistake -> {
                    openWrongDialog.value = true
                }
                UIEvent.Undo -> {
                    scaffoldState.snackbarHostState.showSnackbar("No Back")
                }
            }
        }
    }

    SudokuTheme(ThemeState.darkModeState.value){

    Box {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = if(ThemeState.darkModeState.value)painterResource(R.drawable.bgdark)
                else painterResource(R.drawable.bgl1),
                contentDescription = "background_image",
                contentScale = ContentScale.FillBounds
            )
    Scaffold(
        backgroundColor = Color.Transparent,
        scaffoldState = scaffoldState,
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "back")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        openPauseDialog.value = true
                    }) {
                        Icon(Icons.Filled.Pause, contentDescription = "back")
                    }

                    IconButton(onClick = {

                        viewModel.onEvent(GameEvent.Theme)
                    }) {
                        if(ThemeState.darkModeState.value)
                            Icon(Icons.Filled.LightMode, contentDescription = "back")
                        else
                            Icon(Icons.Filled.DarkMode, contentDescription = "back")
                    }

                }
            }
        },

        ) {

        if (openPauseDialog.value) {
            viewModel.onEvent(GameEvent.OnStop)
            PauseDialog({
                viewModel.onEvent(GameEvent.OnContinue)
                openPauseDialog.value = false
            }, viewModel.timerState,viewModel)
        }
        if (openWrongDialog.value) {
            viewModel.onEvent(GameEvent.OnStop)
            WrongDialog(onDismiss = {
                viewModel.onEvent(GameEvent.OnPlayAgain)
                openWrongDialog.value = false
            }
            ) {
                Toast.makeText(ctx, "No Ads!", Toast.LENGTH_SHORT).show()

            }
        }
        if (openHintDialog.value) {
            viewModel.onEvent(GameEvent.OnStop)
            HintDialog(onDismiss = {
                viewModel.onEvent(GameEvent.OnContinue)
                openHintDialog.value = false
            }) {
                Toast.makeText(ctx, "No Ads!", Toast.LENGTH_SHORT).show()

            }
        }
        if (openWinDialog.value) {
            viewModel.onEvent(GameEvent.OnStop)
            WinDialog(
                onPlayAgain = {
                    viewModel.insertStatistic()
                    viewModel.onEvent(GameEvent.OnRestart)
                    openWinDialog.value = false
                },
                onExit = {
                    viewModel.insertStatistic()
                    navController.navigateUp()
                },
                time = viewModel.timerState,
                difficulty = viewModel.difficulty
            )
        }


        if (!viewModel.sudokuState.value.isReady)
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                CircularProgressIndicator()
                LaunchedEffect(key1 = true) {
                    viewModel.onEvent(GameEvent.OnCreate(viewModel.difficulty))
                }
            }
        else
            SudokuBoard(viewModel)
    }
    }
}
}

@Composable
fun SudokuBoard(
    viewModel: GameViewModel
) {
    val adWidth = LocalConfiguration.current.screenWidthDp
    var timerState by remember {
        mutableStateOf("")
    }

    viewModel.subTimerState = {
        timerState = it.toTime()
    }

//    Timer(time = 0L, viewModel =viewModel )
    BoxWithConstraints {

        val screenWidth = with(LocalDensity.current) {
            constraints.maxWidth.toDp()
        }
        val margin=25

        ConstraintLayout {

            val (board,boardTop, adds, diff, inputs) = createRefs()

            Row(
                Modifier
                    .fillMaxWidth(0.9f)
                    .constrainAs(boardTop) {
                        bottom.linkTo(board.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                , horizontalArrangement = Arrangement.SpaceBetween)
            {

                Text(text = "Mistake ${viewModel.sudokuState.value.mistake}/3", fontWeight = FontWeight.Bold, fontSize = 19.sp)
                Text(text =  timerState, fontWeight = FontWeight.Bold, fontSize = 19.sp)
                Text(text = viewModel.difficulty.toLocalizedResource, fontWeight = FontWeight.Bold, fontSize = 19.sp)
            }

            Box(Modifier
                .constrainAs(board) {
                    top.linkTo(boardTop.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(diff.top)
                }
                .background(MaterialTheme.colors.surface)
                .size(screenWidth - margin.dp)
                .border(
                    width = 2.dp,
                    color = BlueViolet1
                )
            ) {

                SudokuTile(
                    viewModel,
                    screenWidth - margin.dp
                )
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .constrainAs(diff) {
                        top.linkTo(board.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(inputs.top)
                    }, horizontalArrangement = Arrangement.SpaceBetween)
            {


                IconButton(onClick = {

                    viewModel.onEvent(GameEvent.Undo)
                }) {
                    Icon(painter = painterResource(R.drawable.replay),
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp),
                        contentDescription = "replay")
                }

                IconButton(onClick = {
//                    mp = MediaPlayer.create(context,currentSound[1])
//                    mp?.start()
                    viewModel.onEvent(GameEvent.Erase) }) {
                    Icon(painter = painterResource(R.drawable.eraser),
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp),
                        contentDescription = "idea")
                }

                IconButton(onClick = {
//                    mp = MediaPlayer.create(context,currentSound[2])
//                    mp?.start()
                    viewModel.onEvent(GameEvent.Pencil) }) {
                    Icon(painter =  painterResource(R.drawable.pencil),
                        tint = if(viewModel.sudokuState.value.pencil) Color.Blue
                        else MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp),contentDescription = "erase")
                }

                IconButton(onClick = {
//                    mp = MediaPlayer.create(context,currentSound[3])
//                    mp?.start()
                    viewModel.onEvent(GameEvent.Hint(false)) }) {
                    Icon(painter = painterResource(R.drawable.hint),
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp),
                        contentDescription = "back")
                    Text(text = viewModel.sudokuState.value.hint.toString(),
                        fontWeight = FontWeight.Bold)
                }

                IconButton(onClick = { viewModel.onEvent(GameEvent.Fast) }) {
                    Icon(painter =  painterResource(R.drawable.fast),
                        tint = if(viewModel.sudokuState.value.fast) Color.Blue
                        else MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp),
                        contentDescription = "back")
                }
            }

            //this container holds the input buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .constrainAs(inputs) {
                        top.linkTo(diff.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                InputButtonRow(
                    viewModel
                )

            }

        }
    }
}