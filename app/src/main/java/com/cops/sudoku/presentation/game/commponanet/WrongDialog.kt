package com.cops.sudoku.presentation.game.commponanet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
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
import com.cops.sudoku.R

@Composable
fun WrongDialog(onDismiss: ()->Unit,onVideo: ()->Unit,) {

    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        title = null, text = null,
        buttons = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.padding(16.dp))

                Image(painter = painterResource(id = R.drawable.wrong),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape) )

                Spacer(modifier = Modifier.padding(16.dp))

                Text(text = "watch video to continue")

                Spacer(modifier = Modifier.padding(16.dp))

                Divider(color = Color.White, thickness = 0.8.dp)

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Button(onClick = { onVideo() }) {

                        Text(text = "Watch video", fontSize = 15.sp)
                    }

                    Button(onClick = { onDismiss() }) {

                        Text(text = "Restart", fontSize = 15.sp)
                    }
                }

            }
        },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        shape = RoundedCornerShape(8.dp)
    )
}