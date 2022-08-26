package com.cops.sudoku.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cops.sudoku.domain.use_case.game.GameUseCases
import com.cops.sudoku.domain.util.Difficulty
import com.cops.sudoku.domain.util.toLocalizedResource
import com.cops.sudoku.domain.util.toTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val gameUseCases: GameUseCases,
) : ViewModel()  {
    val gameState= mutableStateOf("")

    init {
        viewModelScope.launch {
            gameUseCases.getSudoku(Difficulty.MEDIUM).also { cat ->
                if(cat != null)
                gameState.value="\tContinue\n${cat.difficulty.toLocalizedResource}  ${cat.elapsedTime.toTime()}"
            }
        }
    }

    //This method take Difficulty to get saved game
    fun onEvent(difficulty: Difficulty){

        viewModelScope.launch {
            gameUseCases.getSudoku(difficulty).also { cat ->
                gameState.value=""
                if(cat != null) {
                    gameState.value =
                        "\tContinue\n${cat.difficulty.toLocalizedResource}  ${cat.elapsedTime.toTime()}"
                }
            }
        }
    }

}