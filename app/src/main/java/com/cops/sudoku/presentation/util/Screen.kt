package com.cops.sudoku.presentation.util

sealed class Screen(val route:String){
    object GameScreen:Screen("game")
    object StatisticScreen:Screen("statistic")
    object HomeScreen:Screen("home")
    object BoardingScreen:Screen("boarding")
    object SettingScreen:Screen("setting")
    object SplashScreen:Screen("splash")
}
