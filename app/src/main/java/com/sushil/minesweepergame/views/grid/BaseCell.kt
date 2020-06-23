/* An abstract class to define behaviour of each cell (view) */

package com.sushil.minesweepergame.views.grid

import android.content.Context
import android.view.View
import com.sushil.minesweepergame.Board
import com.sushil.minesweepergame.Variables

abstract class BaseCell (context: Context) : View(context) {
    var app = Variables()
    private var value = 0
    private var isBomb = false
    private var isRevealed = false
    private var isClicked = false
    private var isFlagged = false
    private var x = 0
    private var y:Int = 0
    private var position = 0

    // Constructor
    open fun BaseCell(context: Context?) {
    }

    // Get Integer value of that cell
    open fun getValue(): Int {
        return value
    }

    // Set Integer value of cell (-1 represents bomb and other positive number represents nearby neighboring bombs.)
    open fun setValue(value: Int) {
        isBomb = false
        isRevealed = false
        isClicked = false
        isFlagged = false
        if (value == -1) {
            isBomb = true
        }
        this.value = value
    }

    // Return True if a  cell is bomb else return false
    open fun isBomb(): Boolean {
        return isBomb
    }

    // Set or remove bomb from a particular cell
    open fun setBomb(bomb: Boolean) {
        isBomb = bomb
    }

    // Return true if a cell is revealed
    open fun isRevealed(): Boolean {
        return isRevealed
    }

    // Set a cell to be revealed
    open fun setRevealed() {
        isRevealed = true
        invalidate()
    }

    // Return true if a cell is clicked
    open fun isClicked(): Boolean {
        return isClicked
    }

    // Set a cell to be clicked
    open fun setClicked() {
        isClicked = true
        isRevealed = true
        //isLongClickable = false
        invalidate()
    }

    // Return true if flag is put on a cell
    open fun isFlagged(): Boolean {
        return isFlagged
    }

    // Set or remove Flag on a cell
    open fun setFlagged(flagged: Boolean) {
        isFlagged = flagged
    }

    // Get x coordinate of a cell
    open fun getXPos(): Int {
        return x
    }

    // Get y coordinate of a cell
    open fun getYPos(): Int {
        return y
    }

    // Get x and y coordinate of a cell.
    open fun getPosition(): Int {
        return position
    }

    // Set x and y coordinate of  a cell
    open fun setPosition(x: Int, y: Int) {
        this.x = x
        this.y = y
        position = y * app.GetCols() + x
        invalidate()
    }





}