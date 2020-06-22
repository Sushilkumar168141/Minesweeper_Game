package com.sushil.minesweepergame

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import com.sushil.minesweepergame.Variables
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.viewbinding.ViewBinding
import com.sushil.minesweepergame.databinding.ActivityMainBinding
import com.sushil.minesweepergame.util.Generator
import com.sushil.minesweepergame.views.grid.Grid

class MainActivity : AppCompatActivity() {
    var app = Variables()
    lateinit var con : Context
    //var context = applicationContext
    //var appContext = applicationContext
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //var intent = Intent(this@MainActivity, Board::class.java)
        //startActivity(intent)

        //var buttonStart = findViewById<Button>(R.id.button_start)
        var buttonStart = binding.buttonStart
        buttonStart.setOnClickListener(View.OnClickListener {
            GoToBoardActivity()
        })

        var buttonCustom = findViewById<Button>(R.id.button_custom_board)
        buttonCustom.setOnClickListener(View.OnClickListener {
            CustomBoardInputs()
        })

        var LastGameTime = findViewById<TextView>(R.id.textViewLastGameTime)
        var BestGameTime = findViewById<TextView>(R.id.textViewBestGameTime)
        GameEngine.getInstance()?.lastGameTime = LastGameTime
        GameEngine.getInstance()?.bestGameTime = BestGameTime



    }
//
//   fun GetInstance(): MainActivity {
//        return instance
//   }

    fun setupBoard(rows: Int, cols:Int, mines: Int) {
        app.SetRows(rows)
        app.SetCols(cols)
        app.SetMines(mines)
        Log.i("Information", "MainActivity : setupBoard : Value of rows count is ${app.GetRows()}, cols count is ${app.GetCols()}, mines count is ${app.GetMines()}.")
        //Generator.generate(app.GetMines(), app.GetCols(), app.GetRows())
    }


    fun GetDifficulty(view : View) {
        Log.i("MainActivity", "inside get difficulty function")
        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.getId()) {
                R.id.radioButtonEasy ->
                    if (checked) {
                        app.SetRows(11)
                        app.SetCols(8)
                        app.SetMines(10)
                        setupBoard(11, 8, 10)
                    }
                R.id.radioButtonMedium ->
                    if (checked) {
                        app.SetRows(20)
                        app.SetCols(15)
                        app.SetMines(40)
                        setupBoard(20, 15, 40)
                    }
                R.id.radioButtonHard ->
                    if (checked) {
                        app.SetRows(30)
                        app.SetCols(20)
                        app.SetMines(102)
                        setupBoard(30, 20, 102)
                    }
            }


        }
    }

    fun CustomBoardInputs() {
        val builder = AlertDialog.Builder(this)
        con = this
        var rows_count_int = 0
        var cols_count_int = 0
        var mines_count_int = 0
        val inflater = this.layoutInflater
        val dialogView : View = inflater.inflate(R.layout.custom_board_input, null)
        val rows = dialogView.findViewById<EditText>(R.id.editText_rows_count_input)
        val cols = dialogView.findViewById<EditText>(R.id.editText_cols_count_input)
        val mines = dialogView.findViewById<EditText>(R.id.editText_mines_count_input)
        with(builder) {
            setView(dialogView)
            setTitle("Custom Board Layout")
            setPositiveButton("Ok", fun(dialog: DialogInterface, id: Int) {
                val rows_count_string: String = rows.text.toString()
                val cols_count_string: String = cols.text.toString()
                val mines_count_string: String = mines.text.toString()
                if (rows_count_string.isNotBlank() && cols_count_string.isNotBlank() && mines_count_string.isNotBlank()) {
                    rows_count_int = rows_count_string.toInt()
                    cols_count_int = cols_count_string.toInt()
                    mines_count_int = mines_count_string.toInt()
                    if (rows_count_int <= 10 || rows_count_int > 40)
                    {
                        Toast.makeText(applicationContext,"Rows count should be between 11 and 40", Toast.LENGTH_SHORT).show()
                    }
                    else if( cols_count_int < 8 || cols_count_int > 25)
                    {
                        Toast.makeText(applicationContext, "Columns count should be between 9 and 25 ", Toast.LENGTH_SHORT).show()
                    }
                    else if( mines_count_int < rows_count_int ) {
                        Toast.makeText(applicationContext, "Number of bombs should be at least equal to rows count", Toast.LENGTH_SHORT).show()
                    }
                    else if(cols_count_int > rows_count_int) {
                        Toast.makeText(applicationContext, "Number of columns should be less than or equal to number of rows", Toast.LENGTH_SHORT).show()
                    }
                    else if (mines_count_int <=  ((rows_count_int * cols_count_int) / 4)) {
                        app.SetRows(rows_count_int)
                        app.SetCols(cols_count_int)
                        app.SetMines(mines_count_int)
                        Log.i(
                            "Information",
                            "MainActivity : CustomBoardInputs : Value of rows count is {$rows_count_string}, cols count is {$cols_count_string}, mines count is {$mines_count_string}."
                        )
                        setupBoard(
                            rows_count_string.toInt(),
                            cols_count_string.toInt(),
                            mines_count_string.toInt()
                        )
                        GoToBoardActivity()
                    }
                    else {
                        Toast.makeText(applicationContext, "Mines count should be less than 1/4 of the minesweeper grid size.", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Toast.makeText(applicationContext, "Please enter valid number of rows, columns and mines", Toast.LENGTH_SHORT).show()
                }

            })
            setNegativeButton("Cancel", fun(dialog: DialogInterface, id: Int ) {
                dialog.cancel()
            })
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun GoToBoardActivity() {
        Log.i("Information", "MainActivity : GoToBoardActivity : Value of rows count is ${app.GetRows()}, cols count is ${app.GetCols()}, mines count is ${app.GetMines()}.")
        //setContentView(R.layout.activity_board)
        setupBoard(app.GetRows(), app.GetCols(), app.GetMines())
        GameEngine.getInstance()?.isFirstClick = true
        //GameEngine.getInstance()?.createGrid(this)
        //GameEngine.getInstance()?.setCellValue(this)
        //val intent =  Intent(applicationContext,  Board::class.java)
        val intent =  Intent(applicationContext,  Board::class.java)
        //intent.putExtra("rows_count", app.GetRows())
        //intent.putExtra("cols_count", app.GetCols())
        //intent.putExtra("mines_count", app.GetMines())
        startActivity(intent)
        //GameEngine().setMinesweeperGrid(app.GetCols(), app.GetRows())
        //GameEngine.getInstance()?.createGrid(getMainContext())
    }
}
//TODO {
// 1. 
//
//
// }