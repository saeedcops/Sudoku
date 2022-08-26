package com.cops.sudoku.presentation.game.commponanet

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.cops.sudoku.R
import com.cops.sudoku.domain.util.toTime
import com.cops.sudoku.presentation.game.GameEvent
import com.cops.sudoku.presentation.game.GameViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun PauseDialog( onDismiss: ()->Unit,time:Long,viewModel: GameViewModel = hiltViewModel()) {

    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        title = null, text = null,
        buttons = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.padding(16.dp))

                Image(painter = painterResource(id = R.drawable.pause),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape) )

                Spacer(modifier = Modifier.padding(16.dp))

                Text(text = time.toTime())

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(viewModel.settingState.value.id.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        viewModel.onEvent(GameEvent.Sound)
                    }) {
                        if(viewModel.settingState.value.sound)
                            Icon(Icons.Filled.VolumeUp, contentDescription = "back")
                        else
                            Icon(Icons.Filled.VolumeMute, contentDescription = "back")
                    }

                    IconButton(onClick = {
                        viewModel.onEvent(GameEvent.Vibration)
                    }) {

                        if(viewModel.settingState.value.vibration)
                            Icon(Icons.Filled.Vibration, contentDescription = "back")
                        else
                            Icon(Icons.Filled.Vibration, tint = Color.Red, contentDescription = "back")
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))

                Divider( thickness = 0.8.dp)

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Button(onClick = { onDismiss() }) {

                        Text(text = "Continue", fontSize = 15.sp)
                    }

                }

            }
        },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        shape = RoundedCornerShape(8.dp)
    )
}