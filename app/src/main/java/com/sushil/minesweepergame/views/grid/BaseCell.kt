package com.sushil.minesweepergame.views.grid

import android.content.Context
import android.view.View
import com.sushil.minesweepergame.Board
import com.sushil.minesweepergame.Variables

public abstract class BaseCell (context: Context) : View(context) {
    var app = Variables()
    private var value = 0
    private var isBomb = false
    private var isRevealed = false
    private var isClicked = false
    private var isFlagged = false
    private var x = 0
    private var y:Int = 0
    private var position = 0

    open fun BaseCell(context: Context?) {
    }

    open fun getValue(): Int {
        return value
    }
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

    open fun isBomb(): Boolean {
        return isBomb
    }

    open fun setBomb(bomb: Boolean) {
        isBomb = bomb
    }

    open fun isRevealed(): Boolean {
        return isRevealed
    }

    open fun setRevealed() {
        isRevealed = true
        invalidate()
    }

    open fun isClicked(): Boolean {
        return isClicked
    }

    open fun setClicked() {
        isClicked = true
        isRevealed = true
        //isLongClickable = false
        invalidate()
    }

    open fun isFlagged(): Boolean {
        return isFlagged
    }

    open fun setFlagged(flagged: Boolean) {
        isFlagged = flagged
    }

    open fun getXPos(): Int {
        return x
    }

    open fun getYPos(): Int {
        return y
    }

    open fun getPosition(): Int {
        return position
    }

    open fun setPosition(x: Int, y: Int) {
        this.x = x
        this.y = y
        position = y * app.GetCols() + x
        invalidate()
    }





}