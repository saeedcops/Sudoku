package com.cops.sudoku.domain.use_case.game

data class GameUseCases(
    val createGame: CreateGame,
    val selectNode: SelectNode,
    val updateNode: UpdateNode,
    val eraseNode: EraseNode,
    val hint: Hint,
    val pencil: Pencil,
    val fast: Fast,
    val undo: Undo,
    val updateSudoku: UpdateSudoku,
    val getSudoku: GetSudoku,
    val deleteSudoku: DeleteSudoku,
)