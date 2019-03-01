package edu.rosehulman.dripchar.lightsout

import android.content.Context

class LightsOutGame()   {

    var board: BooleanArray = BooleanArray(ARR_SIZE)
    var gamestate = GameState.NEW
    var count: Int = 0

    lateinit var context: Context

    constructor(context: Context): this() {
        this.context = context
    }

    init    {
        resetGame()
    }

    fun resetGame() {
        board = BooleanArray(ARR_SIZE)
        populateArray(board)
        gamestate = GameState.NEW
        count = 0
    }


    fun populateArray(a: BooleanArray)  {
        for(i in 0 until ARR_SIZE)  {
            var r:Double = Math.random()
            if(r < 0.5){
                a[i] = true
            } else {
                a[i] = false
            }
        }
    }

    fun pressButtonAt(index: Int) {
        if (index !in 0 until ARR_SIZE){
            return
        }

        if(gamestate != GameState.WIN ){
            count++
            if(count > 0 && count < 2){
                gamestate = GameState.TURN_ONE
            } else {
                gamestate = GameState.TURN_MORE_THAN_ONE
            }
            if(index == 0) {
                board[index] = !board[index]
                board[index + 1] = !board[index + 1]
            } else if(index == ARR_SIZE - 1){
                board[index] = !board[index]
                board[index - 1] = !board[index - 1]
            } else {
                board[index] = !board[index]
                board[index + 1] = !board[index + 1]
                board[index - 1] = !board[index - 1]
            }

        }

        checkForWin()
    }

    fun stringForButtonAt(index: Int): String   {
        if(index in 0 until ARR_SIZE)  {
            if(board[index]){
                return "1"
            }
            if(!board[index]){
                return "0"
            }
        }

        return " "
    }

    private fun checkForWin()   {
        var first = board[0]
        for(i in 1 until ARR_SIZE){
            if(first == board[i]){
                //keep going
            } else {
                return
            }
        }
        gamestate = GameState.WIN
    }


    fun stringForGameState(): String {
        return when (gamestate) {
            GameState.NEW   ->  context.getString(R.string.new_game)
            GameState.TURN_ONE  ->  "You have taken " + count + " try"
            GameState.TURN_MORE_THAN_ONE    -> "You have taken " + count + " tries"
            GameState.WIN   ->  context.getString(R.string.win)
        }
    }

    enum class GameState {
        NEW,
        TURN_ONE,
        TURN_MORE_THAN_ONE,
        WIN
    }

    companion object{
        val ARR_SIZE = 7
    }


}