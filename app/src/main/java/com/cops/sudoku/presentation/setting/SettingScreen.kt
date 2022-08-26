package com.cops.sudoku.presentation.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cops.sudoku.R
import com.cops.sudoku.presentation.util.Screen
import com.cops.sudoku.ui.theme.SudokuTheme
import com.cops.sudoku.ui.theme.ThemeState
@Composable
fun SettingScreen(navController: NavController,
                  viewModel: SettingViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()

    SudokuTheme(ThemeState.darkModeState.value){
    /*
   * This Box is to set background image to scaffold
   * */
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = if (ThemeState.darkModeState.value) painterResource(R.drawable.bgdark)
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
                        Text(text = "Settings")
                    },
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            // below line is use to
                            // specify navigation icon.
                            Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "back")
                        }
                    },
                )
            },

            ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(text = "Sounds", style = MaterialTheme.typography.h6)

                    Switch(
                        checked = viewModel.settingState.value.sound,
                        onCheckedChange = { viewModel.onEvent(SettingEvent.Sound) })

                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(text = "Theme", style = MaterialTheme.typography.h6)

                    Switch(checked = ThemeState.darkModeState.value, onCheckedChange = {
                        viewModel.onEvent(SettingEvent.Theme)
                    })

                }
//
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(text = "Vibration", style = MaterialTheme.typography.h6)

                    Switch(checked = viewModel.settingState.value.vibration, onCheckedChange = {
                        viewModel.onEvent(SettingEvent.Vibration)
                    })

                }
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.BoardingScreen.route)
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(text = "How to play", style = MaterialTheme.typography.h6)

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.LiveHelp, contentDescription = "f")
                    }


                }
            }

        }
    }
    }
}