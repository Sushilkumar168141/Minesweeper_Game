package com.sushil.minesweepergame.views.grid

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import com.sushil.minesweepergame.GameEngine
import com.sushil.minesweepergame.Variables


class Grid(context: Context, attrs: AttributeSet?) : GridView(context, attrs) {
    val app = Variables()
    val WIDTH = app.GetCols()
    val HEIGHT = app.GetRows()
    val BOMB_NUMBER = app.GetMines()
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //var expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2, MeasureSpec.AT_MOST)
        //super.onMeasure(widthMeasureSpec, expandSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //super.onMeasure(WIDTH, HEIGHT)
    }

    private inner class GridAdapter : BaseAdapter() {
        override fun getCount(): Int {
            Log.i("Grid", "Grid : Grid Adapter : width = $WIDTH, height = $HEIGHT")
            return WIDTH * HEIGHT
        }

        override fun getItem(position: Int): Any? {
            return null
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            //Log.i("Grid", "Position = $position")
            this.notifyDataSetChanged()
            return GameEngine.getInstance()?.getCellAt(position)
        }
    }

    init {
        GameEngine.getInstance()?.createGrid(context)
        numColumns = WIDTH
        adapter = GridAdapter()
    }
}