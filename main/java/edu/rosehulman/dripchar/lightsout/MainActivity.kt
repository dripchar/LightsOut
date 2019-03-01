package edu.rosehulman.dripchar.lightsout

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    val game = LightsOutGame(this)
    val loButtons = arrayOfNulls<Button>(LightsOutGame.ARR_SIZE)
    var enabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        game.resetGame()
        updateView()

        new_game_button.setOnClickListener() {
            game.resetGame()
            for (i in 0 until LightsOutGame.ARR_SIZE) {
                val id = resources.getIdentifier("button" + i, "id", packageName)
                loButtons[i] = findViewById(id)
                loButtons[i]?.isEnabled = true
                enabled = true
            }
            updateView()
        }

        for (i in 0 until LightsOutGame.ARR_SIZE) {
            val id = resources.getIdentifier("button" + i, "id", packageName)
            loButtons[i] = findViewById(id)
            loButtons[i]?.setOnClickListener {
                game.pressButtonAt(i)
                updateView()
            }
        }
        updateView()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putBooleanArray("my_array", game.board)
        outState?.putSerializable("game state", game.gamestate)
        outState?.putInt("count", game.count)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        game.gamestate = savedInstanceState?.getSerializable("game state") as LightsOutGame.GameState
        game.board = savedInstanceState!!.getBooleanArray("my_array")
        game.count = savedInstanceState?.getInt("count")

        updateView()
    }


    fun updateView() {
        textView.text = game.stringForGameState()

        for (i in 0 until LightsOutGame.ARR_SIZE) {

            if(game.gamestate == LightsOutGame.GameState.WIN){
                loButtons[i]?.isEnabled = false
                enabled = false
            }
                loButtons[i]?.text = game.stringForButtonAt(i)
            }

    }
}
