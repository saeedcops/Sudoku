package com.cops.sudoku.presentation.statistic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cops.sudoku.R
import com.cops.sudoku.domain.util.Difficulty
import com.cops.sudoku.domain.util.toLocalizedResource
import com.cops.sudoku.domain.util.toTime
import com.cops.sudoku.ui.theme.ThemeState

@Composable
fun StatisticScreen(navController: NavController,viewModel: StatisticViewModel = hiltViewModel()) {

    val scaffoldState = rememberScaffoldState()
    val difficulty:List<Difficulty> = listOf(
        Difficulty.EASY,
        Difficulty.MEDIUM,
        Difficulty.HARD,
        Difficulty.EXPERT)

    var dif by remember { mutableStateOf(0) }
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
    Scaffold(
        backgroundColor = Color.Transparent,
        scaffoldState = scaffoldState,

        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                title = {
                    Text(text = "Statistics")
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                navigationIcon = {
                    // navigation icon is use
                    // for drawer icon.
                    IconButton(onClick = {navController.navigateUp() }) {
                        // below line is use to
                        // specify navigation icon.
                        Icon(Icons.Filled.ArrowBackIosNew,contentDescription = "back")
                    }
                },
            )
        },

    ) {

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            /*
            * This Row to select difficulty to show history of Game
            * */
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if (dif > 0){
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


            /*
            * This LazyColumn to show history of Game
            * */
            LazyColumn(){
                item {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(text = "Difficulty", fontWeight = FontWeight.Bold)
                        Text(text = "\t\tTime", fontWeight = FontWeight.Bold)
                        Text(text = "Mistakes", fontWeight = FontWeight.Bold)
                    }
                }
                items(viewModel.statisticState.value){

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(text = it.difficulty.toLocalizedResource)
                        Text(text = it.elapsedTime.toTime())
                        Text(text = it.mistake.toString())
                    }
                    Divider(Modifier.height(1.dp).fillMaxWidth())
                }
            }
        }
    }
    }

}