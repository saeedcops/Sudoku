package com.cops.sudoku.presentation.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cops.sudoku.presentation.boarding.BoardingScreen
import com.cops.sudoku.presentation.game.GameScreen
import com.cops.sudoku.presentation.home.HomeScreen
import com.cops.sudoku.presentation.setting.SettingScreen
import com.cops.sudoku.presentation.splash.SplashScreen
import com.cops.sudoku.presentation.statistic.StatisticScreen

@Composable
fun NavGraph() {
    val navController= rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(route = Screen.BoardingScreen.route) {
            BoardingScreen(navController)
        }
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(route = Screen.GameScreen.route+"?diff={diff}",
            arguments = listOf(
                navArgument("diff"){
                    type= NavType.IntType
                    defaultValue = 1
                }
            )) {
            GameScreen(navController)
        }

        composable(route = Screen.StatisticScreen.route) {
            StatisticScreen(navController)
        }

        composable(route = Screen.SettingScreen.route) {
            SettingScreen(navController)
        }
    }
}
