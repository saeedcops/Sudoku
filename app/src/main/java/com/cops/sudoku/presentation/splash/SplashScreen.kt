package com.cops.sudoku.presentation.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cops.sudoku.R
import com.cops.sudoku.presentation.util.Screen
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController,
                 splashViewModel: SplashViewModel= hiltViewModel()) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    // AnimationEffect
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        splashViewModel.onEvent()
        delay(1000L)

        if(splashViewModel.status.value){
            // navigate to home screen
            if(splashViewModel.isSaved.value){
                navController.navigate(Screen.HomeScreen.route){
                popUpTo(Screen.SplashScreen.route) {
                                inclusive = true
                            }
                }
            // navigate to how to play screen
            }else{
                navController.navigate(Screen.BoardingScreen.route){
                    popUpTo(Screen.SplashScreen.route) {
                        inclusive = true
                    }
                }
            }
        }

    }

    // Image
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value))
    }
}






