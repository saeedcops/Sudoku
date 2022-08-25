package com.cops.sudoku.presentation.game

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cops.sudoku.MainApp
import com.cops.sudoku.R
import com.cops.sudoku.domain.model.Game
import com.cops.sudoku.domain.model.Setting
import com.cops.sudoku.domain.model.Statistic
import com.cops.sudoku.domain.use_case.game.GameUseCases
import com.cops.sudoku.domain.use_case.setting.SettingUseCases
import com.cops.sudoku.domain.use_case.statistic.StatisticUseCases
import com.cops.sudoku.domain.util.*
import com.cops.sudoku.ui.theme.ThemeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameUseCases: GameUseCases,
    private val statisticUseCases: StatisticUseCases,
    private val settingUseCases: SettingUseCases,
    private val sudokuPuzzle: SudokuPuzzle,
    private val ctx: MainApp,
    val setting: Setting,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var mp: MediaPlayer? = null
    var currentSound = mutableListOf<Int>(
        R.raw.clear,
        R.raw.correct,
        R.raw.hint,
        R.raw.loss,
        R.raw.restart,
        R.raw.undo,
        R.raw.win,
        R.raw.wrong)


    private var timerTracker: Job? = null
    var subTimerState: ((Long) -> Unit)? = null
    var timerState: Long = 0L
    var isActive = mutableStateOf(true)
//    var settingState = mutableStateOf(setting)
    val settingState = mutableStateOf<Setting>(setting)
    private var isSaved=false
    private var lastX =-1
    private var lastY =-1
    private val _eventFlow = MutableSharedFlow<UIEvent>()

    val eventFlow = _eventFlow.asSharedFlow()
    var difficulty: Difficulty= Difficulty.EASY

    private val _sudokuState = mutableStateOf(sudokuPuzzle)
    val sudokuState: State<SudokuPuzzle> = _sudokuState

    init {

        savedStateHandle.get<Int>("diff")?.let { diff ->
            when(diff){
                    -4 ->{isSaved =true
                        difficulty = Difficulty.EASY}
                    -3 -> {isSaved=true
                        difficulty = Difficulty.MEDIUM}
                    -2 -> {isSaved=true
                        difficulty = Difficulty.HARD}
                    -1 -> {isSaved=true
                        difficulty = Difficulty.EXPERT}
                    0 -> difficulty = Difficulty.EASY
                    1 -> difficulty = Difficulty.MEDIUM
                    2 -> difficulty = Difficulty.HARD
                    3 -> difficulty = Difficulty.EXPERT
                 }
            _sudokuState.value.difficulty=difficulty
            }
//
    }


    private fun setSound(id:Int){

        if(!setting.sound)
            return
        mp?.reset()
        mp?.release()
        mp=null
        mp = MediaPlayer.create(ctx.applicationContext ,currentSound[id])

        mp?.start()
    }

    private fun cancelStuff() {
        if (timerTracker?.isCancelled == false) timerTracker?.cancel()
    }

     private fun startCoroutineTimer(
    ) = viewModelScope.launch {
        while (isActive.value) {
            delay(1000)
            timerState++
            subTimerState?.invoke(timerState)
            _sudokuState.value.elapsedTime=timerState
        }
    }

    fun onEvent(event: GameEvent) {
        when (event) {
            GameEvent.OnCreate(difficulty) -> {
                viewModelScope.launch {
                    _eventFlow.emit(UIEvent.LOADING)
                    if(isSaved){

                        gameUseCases.getSudoku(difficulty).also { cat ->
                            if(cat != null) {
//                                setSound(4)
                                _sudokuState.value.difficulty = cat.difficulty
                                difficulty = cat.difficulty
                                timerState = cat.elapsedTime
                                _sudokuState.value.mistake = cat.mistake
                                _sudokuState.value.hint = cat.hint
                                _sudokuState.value.graph.restore(cat)
                                _sudokuState.value.isReady = true
                            }

                        }
                    }
                    else
                    _sudokuState.value = _sudokuState.value.setValue()
                    setSound(4)
                    _eventFlow.emit(UIEvent.ACTIVE)
                    timerTracker = startCoroutineTimer()
                }

//                updateDatabase()
            }
            GameEvent.Erase -> {
                setSound(0)
                _sudokuState.value = gameUseCases.eraseNode(sudokuState.value)
                    .copy(reload = !sudokuState.value.reload)
                updateDatabase()

            }
            GameEvent.Fast -> {
                setSound(0)
                _sudokuState.value = gameUseCases.fast(sudokuState.value)
                    .copy(reload = !sudokuState.value.reload)
            }

            is GameEvent.OnInput -> {
                _sudokuState.value =
                    gameUseCases.updateNode(event.input, sudokuState.value,
                        {
                            viewModelScope.launch {
                                setSound(3)
                                _eventFlow.emit(UIEvent.Mistake)
                            }
                        }, {
                            viewModelScope.launch {
                                setSound(6)
//                                gameUseCases.deleteSudoku()
                                _eventFlow.emit(UIEvent.COMPLETE)

                            }
                        },{
                            if(it) {
                                vibrate()
                                setSound(7)
                            }
                            else
                                setSound(1)
                        })
                        .copy(reload = !sudokuState.value.reload)

                updateDatabase()
            }
            GameEvent.OnLoading -> TODO()
            GameEvent.OnPlayAgain -> {
                viewModelScope.launch {
                    setSound(4)
                    _eventFlow.emit(UIEvent.LOADING)
                    _sudokuState.value.graph.restart()
                    _sudokuState.value.mistake = 0

                    timerState = 0
                    _eventFlow.emit(UIEvent.ACTIVE)
                    isActive.value = true
                    timerTracker = startCoroutineTimer()

                }
                updateDatabase()
            }
            GameEvent.OnRestart -> {
                viewModelScope.launch {
                    setSound(4)
                    _eventFlow.emit(UIEvent.LOADING)
                    _sudokuState.value.graph.initialize()
                    _sudokuState.value = _sudokuState.value.setValue()
                    _sudokuState.value.mistake = 0
                    timerState = 0
                    _eventFlow.emit(UIEvent.ACTIVE)
                    isActive.value = true
                    timerTracker = startCoroutineTimer()
                }
                updateDatabase()
            }
            GameEvent.OnContinue -> {
                isActive.value = true
                startCoroutineTimer()
            }
            GameEvent.OnStop -> {
                updateDatabase()
                isActive.value = false
                cancelStuff()
            }
            is GameEvent.OnTileFocused -> {
                _sudokuState.value.elapsedTime = timerState
                _sudokuState.value =
                    gameUseCases.selectNode(event.x, event.y, lastX, lastY, sudokuState.value, {
                        viewModelScope.launch {
//                            gameUseCases.deleteSudoku()
                            setSound(6)
                            _eventFlow.emit(UIEvent.COMPLETE)
                        }
                    },{
                        viewModelScope.launch {
                            setSound(3)
                            _eventFlow.emit(UIEvent.Mistake)
                        }
                    },{
                        if(it) {
                            vibrate()
                            setSound(7)
                        }
                        else
                            setSound(1)
                    })
                        .copy(reload = !sudokuState.value.reload)
                lastX = event.x
                lastY = event.y
            }
            GameEvent.Pencil -> {
                setSound(0)
                _sudokuState.value = gameUseCases.pencil(sudokuState.value)
                    .copy(reload = !sudokuState.value.reload)
            }
            GameEvent.Undo -> {
                setSound(5)
                _sudokuState.value = gameUseCases.undo(sudokuState.value) {
                    viewModelScope.launch {
                        _eventFlow.emit(UIEvent.Undo)
                    }
                }
                    .copy(reload = !sudokuState.value.reload)
            }
            is GameEvent.OnCreate -> TODO()
            is GameEvent.Theme -> {
                 setting.dark = !setting.dark
                viewModelScope.launch {
                    settingUseCases.insertSetting(setting)
                }
                ThemeState.darkModeState.value = setting.dark

            }
            GameEvent.Sound -> {
                settingState.value = setting.copy(sound = !setting.sound)
                setting.sound = !setting.sound
                viewModelScope.launch {
                    settingUseCases.insertSetting(setting)
                }

            }
            GameEvent.Vibration -> {
                settingState.value = setting.copy(vibration = !setting.vibration)
                setting.vibration = !setting.vibration
                viewModelScope.launch {
                    settingUseCases.insertSetting(setting)
                }

            }
            is GameEvent.Hint -> {
                setSound(2)
                _sudokuState.value = gameUseCases.hint(sudokuState.value, {
                    viewModelScope.launch {
                        _eventFlow.emit(UIEvent.Hint)

                    }
//                    return@hint true
                }, {
                    viewModelScope.launch {
//                        gameUseCases.deleteSudoku()
                        _eventFlow.emit(UIEvent.COMPLETE)

                    }
                },event.reward)
                    .copy(reload = !sudokuState.value.reload)
                updateDatabase()
            }

        }
    }

    fun insertStatistic(){
        viewModelScope.launch {
            statisticUseCases.insertStatistic(
                Statistic(
                difficulty = _sudokuState.value.difficulty,
                elapsedTime = timerState, mistake = _sudokuState.value.mistake,
                hint = _sudokuState.value.hint))
//            if (delete)
                gameUseCases.deleteSudoku(difficulty)
        }
    }


    private fun vibrate(){
        if(!setting.vibration)
            return
        val vibrator= (ctx.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O )
            vibrator.vibrate(VibrationEffect.createOneShot(70,VibrationEffect.DEFAULT_AMPLITUDE))
        else
        vibrator.vibrate(70)
    }

    private fun updateDatabase(){
        viewModelScope.launch {
            gameUseCases.updateSudoku(
                Game(
                    difficulty,timerState,
                    _sudokuState.value.mistake,
                    _sudokuState.value.hint,
                    _sudokuState.value.graph.generateBoard(),
                    _sudokuState.value.graph.generateNumberList(),
                    _sudokuState.value.graph.generateStack()
                )
            )
        }
    }
}
