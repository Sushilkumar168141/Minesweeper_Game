package com.sushil.minesweepergame

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.GridLayoutAnimationController
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.sushil.minesweepergame.views.grid.Cell
import com.sushil.minesweepergame.Variables
import com.sushil.minesweepergame.util.Generator
import com.sushil.minesweepergame.util.PrintGrid
import  com.sushil.minesweepergame.R
import com.sushil.minesweepergame.databinding.ActivityBoardBinding
import com.sushil.minesweepergame.views.grid.AppContext
import com.sushil.minesweepergame.views.grid.Grid
import kotlinx.android.synthetic.main.activity_board.*
import kotlinx.android.synthetic.main.activity_board.view.*
import org.w3c.dom.Text
import java.nio.file.Files.find
import java.sql.Time
import java.util.*

class GameEngine : AppCompatActivity() {

    var app = Variables()
    var WIDTH = app.GetCols()
    var HEIGHT = app.GetRows()
    var BOMB_NUMBER = app.GetMines()
    var flag = 0
    var bomb = BOMB_NUMBER
    var isFirstClick = true
    var context: Context? = null
    lateinit var GeneratedGrid : Array<IntArray>
    var MinesweeperGrid = Array(25) { arrayOfNulls<Cell>(40) }
    var FlagRemainingtextview : TextView? = null
    lateinit var buttonRestart : Button
    //lateinit var binding : ActivityBoardBinding
    lateinit var con : Context
    var timertextview  : TextView? = null
    var RestartButton : Button? = null
    var lastGameTime : TextView? = null
    var bestGameTime : TextView? = null
    var bestTime = 0
    var time = 0
    var minutes_string = ""
    var seconds_string = ""
    var cellsNotClicked = 0
    var gameWon = false
    var gameLost = false
    var newgame = true
    var newSharedPreferences : SharedPreferences? = null
    var handler = Handler()
    var runnableCode = object : Runnable {
        override fun run() {
            //TODO("Not yet implemented")
            Log.i("djfsdk","fjdfkj")
        }
    }
    //var binding = ActivityBoardBinding.inflate(layoutInflater)
    //var binding = inflater.inflate(R.layout.activity_board, null)

    fun GameEngine() {
    }
    companion object {
        private var instance: GameEngine? = null
        fun getInstance(): GameEngine? {
            if (instance == null) {
                instance = GameEngine()
            }
            return instance
        }
    }

    fun createGrid(context: Context) {
        Log.e("GameEngine", "createGrid is working")
        this.context = context
        //con = context
        //Log.i("Information", "Value of rows count is $rows_count, cols count is $cols_count, mines count is $mines_count.")
        // create the grid and store it
        //GeneratedGrid= Generator.generate(BOMB_NUMBER, WIDTH, HEIGHT)
        //PrintGrid.print(GeneratedGrid, WIDTH, HEIGHT)
        setGrid(context)
    }

    //private fun setGrid(context: Context, grid: Array<IntArray>) {
    fun setGrid(context: Context) {
        Log.i("GameEngine SetGrid","app.GetRows = ${app.GetRows()}, app.GetCols = ${app.GetCols()}")
        //WIDTH = app.GetCols()
        //HEIGHT = app.GetRows()
        for (x in 0 until app.GetCols()) {
            for (y in 0 until app.GetRows()) {
                if (MinesweeperGrid[x][y] == null) {
                    MinesweeperGrid[x][y] = Cell(context, x, y)
                    getCellAt(x,y)!!.isClickable = true
                    getCellAt(x,y)!!.isLongClickable = true
                }
                /*MinesweeperGrid[x][y]!!.setValue(grid[x][y])
                MinesweeperGrid[x][y]!!.isLongClickable = true*/
                MinesweeperGrid[x][y]!!.invalidate()
                cellsNotClicked = app.GetRows() * app.GetCols()
            }
        }
    }

    //private fun setCellValue(context:Context, grid:Array<IntArray>) {
    fun setCellValue(context:Context) {
        GeneratedGrid= Generator.generate(app.GetMines(), app.GetCols(), app.GetRows())
        PrintGrid.print(GeneratedGrid, app.GetCols(), app.GetRows())
        for (x in 0 until app.GetCols()) {
            for (y in 0 until app.GetRows()) {
                if (MinesweeperGrid[x][y] == null) {
                    MinesweeperGrid[x][y] =
                        Cell(context, x, y)
                }
                MinesweeperGrid[x][y]!!.setValue(GeneratedGrid[x][y])
                MinesweeperGrid[x][y]!!.isLongClickable = true
                MinesweeperGrid[x][y]!!.invalidate()
            }
        }
        isFirstClick = true
    }

    fun getCellAt(position: Int): Cell? {
        //val x = position % WIDTH
        //val y = position / WIDTH
        val x = position % app.GetCols()
        val y = position / app.GetCols()
        Log.i("Information", "GameEngine : GetCellAt : position = $position, width = ${app.GetCols()}, x = $x, y = $y")
        return MinesweeperGrid[x][y]
    }

    fun getCellAt(x: Int, y: Int): Cell? {
        return MinesweeperGrid[x][y]
    }

    fun click(x: Int, y: Int) {
        if (isFirstClick) {
            //setCellValue(con)
            while (getCellAt(x, y)!!.getValue() == -1) {
                Log.i("GameEngine ", "Bomb Clicked, Setting up grid again")
                setCellValue(con)
            }
            isFirstClick = false
            time = 0
            timer()
            handler.post(runnableCode)
            //Board().timer()

        }
        //newgame = false
        if (x >= 0 && y >= 0 && x < app.GetCols() && y < app.GetRows() && !getCellAt(x, y)!!.isClicked()) {
            getCellAt(x, y)!!.setClicked()
            cellsNotClicked-=1
            getCellAt(x,y )!!.isLongClickable = false
            if (getCellAt(x, y)!!.getValue() == 0) {
                for (xt in -1..1) {
                    for (yt in -1..1) {
                        if (xt != yt) {
                            click(x + xt, y + yt)
                        }
                    }
                }
            }
            checkEnd()
            if (getCellAt(x, y)!!.isBomb()) {
                onGameLost()
            }
        }
        //checkEnd()
    }

    private fun checkEnd(): Boolean {
        var bombNotFound = app.GetMines()
        var notRevealed = app.GetCols() * app.GetRows()
        var a = 0
        var b = 0
        for (x in 0 until app.GetCols()) {
            for (y in 0 until app.GetRows()) {
                if (getCellAt(x, y)!!.isRevealed() || getCellAt(x, y)!!.isFlagged()) {
                    notRevealed--
                    //textViewFlagRemaining.setText(notRevealed.toString())
                }
                if (getCellAt(x, y)!!.isFlagged() && getCellAt(x, y)!!.isBomb()) {
                    bombNotFound--
                }
                // block added by me
                if (cellsNotClicked == app.GetMines() && !getCellAt(x, y)!!.isClicked()) {
                    //getCellAt(x, y)!!.isClickable = false
                    a = x
                    b = y
                    if (getCellAt(a,b)!!.isBomb()) {
                        //Toast.makeText(context, "Game won", Toast.LENGTH_SHORT).show()
                        onGameWon(a,b)
                    }
                }
            }
        }
        //Log.i("GameEngine, Check End", "cells not clicked  = $cellsNotClicked")
        /*if (bombNotFound == 0 && notRevealed == 0) {
            //Toast.makeText(context, "Game won", Toast.LENGTH_SHORT).show()
            onGameWon()
        }*/
        return false
    }

    fun flag(x: Int, y: Int) {
        //var bomb = app.GetMines()
        //flag++
        isFirstClick = false
        var isFlagged = getCellAt(x, y)!!.isFlagged()
        getCellAt(x, y)!!.setFlagged(!isFlagged)
        if (getCellAt(x,y)!!.isFlagged()) {
            flag++
        }
        else if (!getCellAt(x,y)!!.isFlagged()) {
            flag--
        }
        FlagRemainingtextview!!.text = (BOMB_NUMBER-flag).toString()
        getCellAt(x, y)!!.invalidate()
    }
    // TODO {"Make the FlagRemaining TextView working"}
    /*private fun updateTextViewFlagRemaining(flagRemaining : Int) {
        *//*var layout = R.layout.activity_board
        textViewFlagRemaining = findViewById<TextView>(R.id.textViewFlagRemaining)
        var isFlagged = getCellAt(x,y)!!.isFlagged()
        if(isFlagged) {
            textViewFlagRemaining.text = BOMB_NUMBER.toString()
        }
        else {
            textViewFlagRemaining.text = BOMB_NUMBER.toString()
        }*//*
        textViewFlagRemaining.text = flagRemaining.toString()
    }*/

    private fun onGameWon(a: Int, b: Int) {
        Toast.makeText(context, "Game Won", Toast.LENGTH_LONG).show()
        handler.removeCallbacks(runnableCode)
        isFirstClick = false

        lastGameTime!!.text = "Last Game Time : $minutes_string:$seconds_string"
        newSharedPreferences!!.edit().putString("Last Game Time", "Last Game Time : $minutes_string:$seconds_string").apply()
        if (time < newSharedPreferences!!.getInt("Best Time", Int.MAX_VALUE)) {
            bestTime = time
            Log.i("GameEngine : timer", "best time = $bestTime")
            bestGameTime!!.text = "Best Game Time : $minutes_string:$seconds_string"
            newSharedPreferences!!.edit().putInt("Best Time", bestTime).apply()
            newSharedPreferences!!.edit().putString("Best Game Time", "Best Game Time : $minutes_string:$seconds_string").apply()
        }

        for (x in 0 until app.GetCols()) {
            for (y in 0 until app.GetRows()) {
                //getCellAt(x, y)!!.setRevealed()
                getCellAt(x, y)!!.setClicked()
                getCellAt(x,y)!!.isClickable = false
                getCellAt(x,y)!!.isLongClickable = false
                //return
            }

        }

    }

    private fun onGameLost() {
        // handle lost game
        //isGameOver = true
        gameLost = true
        Toast.makeText(context, "Game lost", Toast.LENGTH_LONG).show()
        handler.removeCallbacks(runnableCode)
        isFirstClick = false
        for (x in 0 until app.GetCols()) {
            for (y in 0 until app.GetRows()) {
                //getCellAt(x, y)!!.setRevealed()
                if (getCellAt(x,y)!!.isFlagged()) {
                    getCellAt(x,y)!!.setFlagged(false)
                }
                getCellAt(x, y)!!.setClicked()
                getCellAt(x,y)!!.isClickable = false
                getCellAt(x,y)!!.isLongClickable = false
                //return
            }

        }
    }

    fun timer() {
        //var time = 0
        //var bestTime = 0
        var seconds = 0
        var minutes = 0
        minutes_string = minutes.toString()
        seconds_string = seconds.toString()
        handler = Handler()
        runnableCode = object : Runnable {
            override fun run() {
                time++
                minutes = time / 60
                seconds = time - (minutes * 60)
                if (minutes < 10) {
                    minutes_string = "0$minutes"
                }
                else {
                    minutes_string = minutes.toString()
                }
                if(seconds < 10) {
                    seconds_string = "0$seconds"
                }
                else {
                    seconds_string = seconds.toString()
                }
                Log.i("GameEngine_Timer", "second = $time")
                //Timertextview.text = "00:10"
                //Timertextview.text = binding.resources.getString(R.string.app_name)
                timertextview!!.text = "$minutes_string:$seconds_string"


                handler.postDelayed(this, 1000)
            }
        }

    }

    /*override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        con = applicationContext
    }*/

}