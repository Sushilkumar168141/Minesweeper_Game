/* Class to set up the  behaviour of various view presents on the second activity */

package com.sushil.minesweepergame

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.sushil.minesweepergame.databinding.ActivityBoardBinding
import com.sushil.minesweepergame.util.Generator
import com.sushil.minesweepergame.util.PrintGrid
import com.sushil.minesweepergame.views.grid.Cell
import com.sushil.minesweepergame.views.grid.Grid
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_board.*
import kotlinx.android.synthetic.main.activity_board.view.*
import org.w3c.dom.Text
import java.sql.Time

class Board : AppCompatActivity() {
    //fun timer() {}
    var app = Variables()
    var rows_count = 0
    var cols_count = 0
    var mines_count = 0
    var context: Context = this
    //var TimerTextView : TextView? = null
    var inflater : LayoutInflater? = null
    var RestartButton : Button? = null
    lateinit var TimerTextView : TextView
    lateinit var FlagRemaining : TextView
    lateinit var minesweeperGridView : View
    lateinit var GameWinImage : ImageView
    lateinit var GameLoseImage : ImageView
    lateinit var binding : ActivityBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //context = this
        binding = ActivityBoardBinding.inflate(layoutInflater)
        val view = binding.root
        //Setting view of the activity
        setContentView(view)

        // Getting reference of the Timer Text View
        //TimerTextView = binding.textViewTimer
        TimerTextView = findViewById<TextView>(R.id.textViewTimer)

        // Getting reference of the Mines to be flagged textview
        FlagRemaining = findViewById(R.id.textViewFlagRemaining)

        // Getting reference of the imageview of game win
        GameWinImage = findViewById<ImageView>(R.id.imageViewgame_win)

        // Getting reference of the imageview of game win
        GameLoseImage = findViewById<ImageView>(R.id.imageViewGameLose)

        // Getting reference of the minesweeper gridview
        minesweeperGridView = findViewById(R.id.minesweeperGridView)

        // Getting reference of the restart button
        RestartButton = binding.buttonRestart //findViewById(R.id.buttonRestart)
        RestartButton!!.setOnClickListener(View.OnClickListener() {
            if (!GameEngine.getInstance()?.gameEnd!!) {
                restartAlert()
            }
            else {
                TimerTextView.text = "00:00"
                FlagRemaining.text = app.GetMines().toString()
                //Log.i("Board: Restart","app.GetMines = ${app.GetMines()}")

                // Setting various variable of the GameEngine class when restart button is clicked.
                GameEngine.getInstance()?.handler!!.removeCallbacks(GameEngine.getInstance()?.runnableCode)
                GameEngine.getInstance()?.timertextview = TimerTextView
                GameEngine.getInstance()?.FlagRemainingtextview = FlagRemaining
                GameEngine.getInstance()?.flag = 0
                GameEngine.getInstance()?.time = 0
                GameEngine.getInstance()?.con = applicationContext
                GameEngine.getInstance()?.isFirstClick = false
                GameEngine.getInstance()?.createGrid(applicationContext)
                GameEngine.getInstance()?.setCellValue(applicationContext)
                GameEngine.getInstance()?.gameEnd = true
                GameEngine.getInstance()?.cellsNotClicked = (app.GetRows()*app.GetCols())
                GameEngine.getInstance()?.minesweeperGridView = minesweeperGridView
                GameEngine.getInstance()?.minesweeperGridView!!.visibility = View.VISIBLE
                GameEngine.getInstance()?.gameWinImage!!.visibility = View.INVISIBLE
                GameEngine.getInstance()?.gameLoseImage!!.visibility = View.INVISIBLE
            }
        })

        // Setting various variable of GameEngine class when a game starts.
        FlagRemaining.text = app.GetMines().toString()
        GameEngine.getInstance()?.handler!!.removeCallbacks(GameEngine.getInstance()?.runnableCode)
        GameEngine.getInstance()?.timertextview = TimerTextView
        GameEngine.getInstance()?.FlagRemainingtextview = FlagRemaining
        GameEngine.getInstance()?.flag = 0
        GameEngine.getInstance()?.time = 0
        GameEngine.getInstance()?.con = this
        GameEngine.getInstance()?.isFirstClick = false
        GameEngine.getInstance()?.createGrid(this)
        GameEngine.getInstance()?.setCellValue(this)
        //GameEngine.getInstance()?.gameWon = true
        GameEngine.getInstance()?.gameEnd = true
        GameEngine.getInstance()?.minesweeperGridView = minesweeperGridView
        //GameEngine.getInstance()?.cellsNotClicked = (app.GetRows()*app.GetCols())
        GameEngine.getInstance()?.gameWinImage = GameWinImage
        GameEngine.getInstance()?.gameLoseImage = GameLoseImage
        GameEngine.getInstance()?.gameWinImage!!.visibility = View.INVISIBLE
        GameEngine.getInstance()?.gameLoseImage!!.visibility = View.INVISIBLE

    }

    // Function  to show the restart alert dialog view.
    fun restartAlert() {
        //Log.i("Board", "Inside Restart Alert")
        val builder = AlertDialog.Builder(this)
        with (builder) {
            setTitle("Restart Game")
            setIcon(R.drawable.warning)
            setMessage("All the progress made in this game will be lost. Do you still want to restart ?")
            setPositiveButton("Yes", fun(dialog: DialogInterface, id: Int) {
                TimerTextView.text = "00:00"
                FlagRemaining.text = app.GetMines().toString()
                //Log.i("Board: Restart","app.GetMines = ${app.GetMines()}")

                // Setting various variable of the GameEngine class when restart button is clicked.
                GameEngine.getInstance()?.handler!!.removeCallbacks(GameEngine.getInstance()?.runnableCode)
                GameEngine.getInstance()?.timertextview = TimerTextView
                GameEngine.getInstance()?.FlagRemainingtextview = FlagRemaining
                GameEngine.getInstance()?.flag = 0
                GameEngine.getInstance()?.time = 0
                GameEngine.getInstance()?.con = applicationContext
                GameEngine.getInstance()?.isFirstClick = false
                GameEngine.getInstance()?.createGrid(applicationContext)
                GameEngine.getInstance()?.setCellValue(applicationContext)
                GameEngine.getInstance()?.gameEnd = true
                GameEngine.getInstance()?.cellsNotClicked = (app.GetRows()*app.GetCols())
                GameEngine.getInstance()?.minesweeperGridView = minesweeperGridView
                GameEngine.getInstance()?.gameWinImage = GameWinImage
                GameEngine.getInstance()?.gameLoseImage = GameLoseImage
                GameEngine.getInstance()?.gameWinImage!!.visibility = View.INVISIBLE
                GameEngine.getInstance()?.gameLoseImage!!.visibility = View.INVISIBLE

            })
            setNegativeButton("No", fun(dialog: DialogInterface, id: Int) {
                dialog.cancel()
            })

        }
        val alertDialog = builder.create()
        alertDialog.show()
    }


}
