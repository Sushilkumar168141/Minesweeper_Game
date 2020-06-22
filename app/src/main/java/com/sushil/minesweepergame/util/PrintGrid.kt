package com.sushil.minesweepergame.util

import android.util.Log

object PrintGrid {
    fun print(grid: Array<IntArray>, width: Int, height: Int) {
        for (x in 0 until height) {
            var printedText = "| "
            for (y in 0 until width) {
                printedText += grid[y][x].toString().replace("-1", "B") + " | "
            }
            Log.i("PrintGrid", printedText)
        }
    }
}