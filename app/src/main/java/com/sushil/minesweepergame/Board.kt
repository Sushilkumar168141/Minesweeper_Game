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
    lateinit var binding : ActivityBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //context = this
        binding = ActivityBoardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //TimerTextView = binding.textViewTimer
        TimerTextView = findViewById<TextView>(R.id.textViewTimer)
        FlagRemaining = findViewById(R.id.textViewFlagRemaining)
        RestartButton = binding.buttonRestart //findViewById(R.id.buttonRestart)
        RestartButton!!.setOnClickListener(View.OnClickListener() {
            restartAlert()
        })
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
        //TimerTextView.text = "00:10"
        //timer()

    }

    fun restartAlert() {
        Log.i("Board", "Inside Restart Alert")
        val builder = AlertDialog.Builder(this)
        //val inflater = this.layoutInflater
        with (builder) {
            setTitle("Restart Game")
            setIcon(R.drawable.warning)
            setMessage("All the progress made in this game will be lost. Do you still want to restart ?")
            setPositiveButton("Yes", fun(dialog: DialogInterface, id: Int) {
                TimerTextView.text = "00:00"
                FlagRemaining.text = app.GetMines().toString()
                Log.i("Board: Restart","app.GetMines = ${app.GetMines()}")
                GameEngine.getInstance()?.handler!!.removeCallbacks(GameEngine.getInstance()?.runnableCode)
                GameEngine.getInstance()?.timertextview = TimerTextView
                GameEngine.getInstance()?.FlagRemainingtextview = FlagRemaining
                GameEngine.getInstance()?.flag = 0
                GameEngine.getInstance()?.time = 0
                GameEngine.getInstance()?.con = applicationContext
                GameEngine.getInstance()?.isFirstClick = false
                GameEngine.getInstance()?.createGrid(applicationContext)
                GameEngine.getInstance()?.setCellValue(applicationContext)
            })
            setNegativeButton("No", fun(dialog: DialogInterface, id: Int) {
                dialog.cancel()
            })

        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun timer() {
        var seconds=0
        //val inflater = LayoutInflater.from(context)
        //val bind = ActivityBoardBinding.inflate(layoutInflater)
        //val bind = inflater.inflate(R.layout.activity_board, null)
        val Timer = binding.textViewTimer
        //var textViewTimer: TextView = findViewById(R.id.textViewTimer)
        //var view = inflater.inflate(R.layout.activity_board, R.layout.activity_main, false)
        //TimerTextView = R.id.
        //Log.i("Board timer","${Board().binding}, $TimerTextView")
        //TimerTextView = binding.textViewTimer
        //TimerTextView!!.text = "00:10"
        var handler = Handler()
        var runnableCode = object : Runnable {
            override fun run() {
                seconds++
                Log.i("GameEngine_Timer", "Runnable has run, A second must have passed")
                Timer.text = seconds.toString()
                //TimerTextView.text = resources.getString(R.string.app_name)
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnableCode)

    }


}
