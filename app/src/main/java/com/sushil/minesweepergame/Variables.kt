/* Class to access variable  in all classes. */

package com.sushil.minesweepergame

import android.app.Application
import android.content.Context
import android.util.Log

class Variables : Application() {
    companion object {
        var rows_count: Int = 11
        var cols_count: Int = 8
        var mines_count: Int = 10
    }


    fun SetRows(rows: Int): Int {
        rows_count = rows
        return rows_count
    }

    fun SetCols(cols: Int): Int {
        cols_count = cols
        return cols_count
    }

    fun SetMines(mines: Int): Int {
        mines_count = mines
        return mines_count
    }

    fun GetRows(): Int {
        //Log.i("information", "Variable : Rows = $rows_count")
        return rows_count
    }

    fun GetCols(): Int {
        //Log.i("information", "Variable : Cols = $cols_count")
        return cols_count
    }

    fun GetMines(): Int {
        //Log.i("information", "Variable : Mines> = $mines_count")
        return mines_count
    }

}