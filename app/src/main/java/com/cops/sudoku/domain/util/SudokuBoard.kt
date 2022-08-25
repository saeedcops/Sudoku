package com.cops.sudoku.domain.util

import com.cops.sudoku.domain.model.Game
import java.io.Serializable
import java.util.*

class SudokuBoard: Serializable {

    private var stack:Stack<LastState> = Stack()
    private var board = LinkedHashMap<Int,SudokuNode>()
    private var numberList = mutableListOf<Int>()
    private var currentRow = -1
    private var currentColomn = -1

    private var chosenList: List<Int> = listOf(1,1,2,2,1)

    init {
     initialize()
//        build()
    }

    fun setDifficulty(difficulty:Difficulty){
        chosenList = when(difficulty){
            Difficulty.EASY -> listOf(1,1,2,1,1)
            Difficulty.MEDIUM -> listOf(1,1,2,2,1)
            Difficulty.HARD -> listOf(1,2,2,2,1)
            Difficulty.EXPERT -> listOf(1,2,2,2,2)
        }
    }
    fun initialize(){
        stack.clear()
        for (r in 0..8) {
            numberList.add(r,9)
            for (c in 0..8) {
                val newNode = SudokuNode(
                    r,
                    c,
                    0,
                    0
                )
                board.set(newNode.hashCode(),newNode)
            }
        }
    }

    fun restore(game: Game){
        board=game.board
        numberList=game.numberList.toMutableList()
        stack=game.stack

    }
    fun generateStack(): Stack<LastState> {

        return stack
    }

    fun generateNumberList(): List<Int> {

        return numberList
    }

    fun generateBoard(): LinkedHashMap<Int,SudokuNode> {

        return board
    }

   fun build(): Boolean  {
        var row = -1
        var colomn = -1
        for (r in 0..8) {
            for (c in 0..8) {
                if (board.get( getHash(r,c))?.correct == 0) {
                    row = r
                    colomn = c
                    break
                }
            }
        }
       // get out of recursion
        if (row == -1 && colomn == -1) {
            return true
        }

        for (i in 1..9) {
            if (i == 1) {
                board.get( getHash(row,colomn))?.correct = Random().nextInt(9) + 1
            } else {

                board.get( getHash(row,colomn))?.correct = i
            }

            if (check(row, colomn)) {
                if (build()) {
                    hideTile(row,colomn)
                    return true
                }
            }
            board.get( getHash(row,colomn))?.correct = 0
        }
        return false
    }

    private fun hideTile(x:Int,y:Int){

        val r=Random().nextInt(5)
        if( chosenList[r] % 2 != 0) {
            board.get(getHash(x, y))?.readOnly = true

            board.get(getHash(x, y))?.correct?.let { numberList.set(it-1,numberList[it-1]-1) }
        }

    }

    private fun check(row: Int, col: Int): Boolean {
        if (board.get( getHash(row,col))!!.correct > 0) {
            for (i in 0..8) {
                if (board.get( getHash(i,col))!!.correct == board.get( getHash(row,col))!!.correct && row != i) {
                    return false
                }
                if (board.get( getHash(row,i))!!.correct == board.get( getHash(row,col))!!.correct && col != i) {
                    return false
                }
            }
            val boxRow = row / 3
            val boxCol = col / 3
            for (r in boxRow * 3 until boxRow * 3 + 3) {
                for (c in boxCol * 3 until boxCol * 3 + 3) {
                    if (board.get( getHash(r,c))!!.correct == board.get( getHash(row,col))!!.correct && col != c && row != r) {
                        return false
                    }
                }
            }
        }
        return true
    }

    private fun saveState(){

        stack.push(LastState(board.get(getHash(currentRow, currentColomn))!!.copy(pencilList =
        board.get(getHash(currentRow, currentColomn))!!.pencilList.toMutableSet()),numberList.toList()))
    }

     fun isCompleted():Boolean{
        var complete=true
        numberList.forEach {
            if (it >0) {
                complete = false
            }
        }

        if (complete){
            for (r in 0..8) {
                for (c in 0..8) {
                    if (board.get(getHash(r,c))?.correct != board.get(getHash(r,c))?.answer
                        && !board.get(getHash(r,c))?.readOnly!!) {
                        complete = false
                        break
                    }
                }
            }
        }

        return complete
    }

    fun restart(){
        while(!stack.isEmpty())
         {
            val state = stack.pop()
            this.numberList = state.numberList.toMutableList()

            board.get(getHash(state.sudokuNode.x, state.sudokuNode.y))?.answer =
                state.sudokuNode.answer
            board.get(getHash(state.sudokuNode.x, state.sudokuNode.y))?.wrong =
                state.sudokuNode.wrong
            board.get(getHash(state.sudokuNode.x, state.sudokuNode.y))?.pencilList =
                state.sudokuNode.pencilList
        }

    }

    fun undo(isEmpty:()->Unit){
        if(stack.isEmpty())
            isEmpty()
        else {
            val state = stack.pop()
            this.numberList = state.numberList.toMutableList()

            board.get(getHash(state.sudokuNode.x, state.sudokuNode.y))?.answer =
                state.sudokuNode.answer
            board.get(getHash(state.sudokuNode.x, state.sudokuNode.y))?.wrong =
                state.sudokuNode.wrong
            board.get(getHash(state.sudokuNode.x, state.sudokuNode.y))?.pencilList =
                state.sudokuNode.pencilList
        }

    }


    fun selectNode(x: Int,y: Int){
        if(currentRow != -1) {
            board.get(getHash(currentRow, currentColomn))?.selected = false
        }
        currentRow=x
        currentColomn=y
        board.get(getHash(x,y))?.selected=true
    }

    fun getNode(x: Int,y: Int):SudokuNode{

       return board.get(getHash(x,y))!!
    }

    fun updateNode(value:Int,isWrong: (Boolean) -> Unit):Int{
        if(currentRow ==-1 || value ==-1 ||
            board.get(getHash(currentRow, currentColomn))!!.readOnly ||
            board.get(getHash(currentRow, currentColomn))!!.answer ==value ||
                numberList.get(value-1) < 1)
            return 0

        saveState()

        numberList.set(value-1,numberList.get(value-1)-1)

        //to get back last inserted number in list
        if (board.get(getHash(currentRow, currentColomn))!!.answer > 0)
            numberList.set(board.get(getHash(currentRow, currentColomn))!!.answer-1,
                numberList.get(board.get(getHash(currentRow, currentColomn))!!.answer-1)+1)

        board.get(getHash(currentRow, currentColomn))?.answer = value
        board.get(getHash(currentRow, currentColomn))?.wrong =
            value != board.get(getHash(currentRow, currentColomn))?.correct

        if (board.get(getHash(currentRow, currentColomn))?.wrong == true) {
            isWrong(true)
            return 1
        }

        isWrong(false)
        return 0
    }



    fun eraseNode(){
        if(currentRow != -1)
        board.get(getHash(currentRow, currentColomn))?.pencilList = mutableSetOf()

        if(currentRow == -1 || board.get(getHash(currentRow, currentColomn))?.answer == 0)
            return
        saveState()
        numberList.set(board.get(getHash(currentRow, currentColomn))?.answer!!-1,
            numberList.get(board.get(getHash(currentRow, currentColomn))?.answer!!-1)+1)


        board.get(getHash(currentRow, currentColomn))?.answer = 0
        board.get(getHash(currentRow, currentColomn))?.wrong = false
    }

    fun updatePencil(value: Int){
        if(currentRow == -1)
            return

        saveState()
        if(board.get(getHash(currentRow,currentColomn))?.pencilList?.size!! > 3){
            val pen = board.get(getHash(currentRow,currentColomn))?.pencilList!!.toMutableList()
            pen.removeFirst()
            board.get(getHash(currentRow,currentColomn))?.pencilList =pen.toHashSet()
        }

        board.get(getHash(currentRow,currentColomn))?.pencilList?.add(value)
    }

    fun hint():Int{
        if(currentRow == -1 || board.get(getHash(currentRow,currentColomn))?.readOnly==true ||
            board.get(getHash(currentRow,currentColomn))?.answer == board.get(getHash(currentRow,currentColomn))?.correct)
            return 0

        saveState()

        if (board.get(getHash(currentRow,currentColomn))?.answer != 0) {
            numberList.set(
                board.get(getHash(currentRow, currentColomn))?.answer!! - 1,
                numberList.get(board.get(getHash(currentRow, currentColomn))?.answer!! - 1) + 1
            )
            board.get(getHash(currentRow,currentColomn))?.wrong=false
        }

        board.get(getHash(currentRow,currentColomn))?.answer = board.get(getHash(currentRow,currentColomn))?.correct!!
        numberList.set(board.get(getHash(currentRow, currentColomn))?.correct!!-1,
            numberList.get(board.get(getHash(currentRow, currentColomn))?.correct!!-1)-1)

        return 1
    }

    fun getSameNumbers(x: Int,y: Int,selected:Boolean){


        if((board.get(getHash(x,y))?.readOnly == false
                    && board.get( getHash(x,y))?.answer == 0 )
            || board.get(getHash(x,y))?.wrong == true)

            return

        val value = if (board.get(getHash(x, y))?.readOnly == true)
            board.get(getHash(x, y))?.correct!!
        else
            board.get(getHash(x, y))?.answer!!

        for (r in 0..8) {
            for (c in 0..8) {
                val node=board.get( getHash(r,c))
                if ((value == node?.answer && !node.wrong) || (node?.readOnly == true && value == node.correct)) {
                    node.selected =selected
                    break
                }
            }
        }
    }


    fun getSameNumbers(value: Int,selected:Boolean){

        if(value == -1)
            return

        for (r in 0..8) {
            for (c in 0..8) {
                val node=board.get( getHash(r,c))
                if ((value == node?.answer && !node.wrong) || (node?.readOnly == true && value == node.correct)) {
                    node.selected =selected
                    break
                }
            }
        }
    }


     fun setShadow(x: Int,y: Int, shadow: Boolean) {

        for (i in 0..8) {
            board.get( getHash(i,y))!!.shadow=shadow
            board.get( getHash(x,i))!!.shadow=shadow
        }
        val boxRow = x / 3
        val boxCol = y / 3
        for (r in boxRow * 3 until boxRow * 3 + 3) {
            for (c in boxCol * 3 until boxCol * 3 + 3) {
                board.get( getHash(r,c))!!.shadow=shadow
            }
        }

    }

    fun setShadow( shadow: Boolean) {

        if(currentRow == -1)
            return
        for (i in 0..8) {
            board.get( getHash(i,currentColomn))!!.shadow=shadow
            board.get( getHash(currentRow,i))!!.shadow=shadow
        }
        val boxRow = currentRow / 3
        val boxCol = currentColomn / 3
        for (r in boxRow * 3 until boxRow * 3 + 3) {
            for (c in boxCol * 3 until boxCol * 3 + 3) {
                board.get( getHash(r,c))!!.shadow=shadow
            }
        }

    }
}