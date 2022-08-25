package com.cops.sudoku.data.data_source

import androidx.room.*
import com.cops.sudoku.domain.model.Game
import com.cops.sudoku.domain.util.Difficulty
import com.cops.sudoku.domain.util.SudokuPuzzle
import kotlinx.coroutines.flow.Flow

@Dao
interface SudokuDoa {

    @Query("SELECT * FROM game where difficulty =:difficulty")
    suspend fun getSudoku(difficulty: Difficulty): Game?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSudoku(game: Game)

    @Query("delete from game where difficulty =:difficulty")
    suspend fun deleteGame(difficulty: Difficulty)

}