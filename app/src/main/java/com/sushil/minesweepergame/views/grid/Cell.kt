package com.sushil.minesweepergame.views.grid


import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.sushil.minesweepergame.Board
import com.sushil.minesweepergame.GameEngine
//import com.sushil.minesweepergame.GameEngine
import com.sushil.minesweepergame.R


class Cell(context: Context, x: Int, y: Int) : BaseCell(context), View.OnClickListener,
    View.OnLongClickListener {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
    var game_engine = GameEngine.getInstance()

    override fun onClick(v: View?) {
        //game_engine?.click(getXPos(), getYPos())
        game_engine?.click(getXPos(), getYPos())
    }


    override fun onLongClick(v: View?): Boolean{
        game_engine?.flag(getXPos(), getYPos())
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d("Minesweeper", "Cell::onDraw")
        drawButton(canvas)
        if (isFlagged()) {
            drawFlag(canvas)
        } else if (isRevealed() && isBomb() && !isClicked()) {
            drawNormalBomb(canvas)
        } else {
            if (isClicked()) {
                if (getValue() == -1) {
                    drawBombExploded(canvas)
                } else {
                    drawNumber(canvas)
                }
            } else {
                drawButton(canvas)
            }
        }
    }

    private fun drawBombExploded(canvas: Canvas) {
        val drawable = ContextCompat.getDrawable(context, R.drawable.bomb_exploded)
        drawable!!.setBounds(0, 0, width, height)
        drawable.draw(canvas)
    }

    private fun drawFlag(canvas: Canvas) {
        val drawable = ContextCompat.getDrawable(context, R.drawable.minesweeper_flag_icon)
        drawable!!.setBounds(0, 0, width, height)
        drawable.draw(canvas)
    }

    private fun drawButton(canvas: Canvas) {
        val drawable = ContextCompat.getDrawable(context, R.drawable.minesweeper_unclicked_cell)
        drawable!!.setBounds(0, 0, width, height)
        drawable.draw(canvas)
    }

    private fun drawNormalBomb(canvas: Canvas) {
        val drawable = ContextCompat.getDrawable(context, R.drawable.bomb_normal)
        drawable!!.setBounds(0, 0, width, height)
        drawable.draw(canvas)
    }

    private fun drawNumber(canvas: Canvas) {
        var drawable: Drawable? = null
        when (getValue()) {
            0 -> drawable = ContextCompat.getDrawable(context, R.drawable.minesweeper_clicked_cell)
            1 -> drawable = ContextCompat.getDrawable(context, R.drawable.number_1)
            2 -> drawable = ContextCompat.getDrawable(context, R.drawable.number_2)
            3 -> drawable = ContextCompat.getDrawable(context, R.drawable.number_3)
            4 -> drawable = ContextCompat.getDrawable(context, R.drawable.number_4)
            5 -> drawable = ContextCompat.getDrawable(context, R.drawable.number_5)
            6 -> drawable = ContextCompat.getDrawable(context, R.drawable.number_6)
            7 -> drawable = ContextCompat.getDrawable(context, R.drawable.number_7)
            8 -> drawable = ContextCompat.getDrawable(context, R.drawable.number_8)
        }
        drawable!!.setBounds(0, 0, width, height)
        drawable.draw(canvas)
    }

    init {
        setPosition(x, y)
        setOnClickListener(this)
        setOnLongClickListener(this)
    }

}