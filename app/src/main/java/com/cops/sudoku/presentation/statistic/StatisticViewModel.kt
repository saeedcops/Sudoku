package com.cops.sudoku.presentation.statistic

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cops.sudoku.domain.model.Statistic
import com.cops.sudoku.domain.use_case.statistic.StatisticUseCases
import com.cops.sudoku.domain.util.Difficulty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val statisticUseCases: StatisticUseCases,
) : ViewModel()  {

    private val _statisticState = mutableStateOf<MutableList<Statistic>>(mutableListOf())
    val statisticState: State<List<Statistic>> = _statisticState

    init {
        viewModelScope.launch {
            statisticUseCases.getStatistics(Difficulty.EASY).onEach { cat ->
                if(cat != null) {
                    cat.forEach {
                        _statisticState.value.add(it)
                    }

                }
            }.launchIn(viewModelScope)
        }
    }

    fun onEvent(difficulty: Difficulty){
        viewModelScope.launch {

            statisticUseCases.getStatistics(difficulty).onEach { cat ->
                _statisticState.value = mutableListOf()
                if(cat != null) {
                    cat.forEach {
                        _statisticState.value.add(it)
                    }

                }
            }.launchIn(viewModelScope)
        }

    }

}