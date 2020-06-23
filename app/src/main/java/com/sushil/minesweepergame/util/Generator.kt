/* Class to generate the minesweeper grid */

package com.sushil.minesweepergame.util

import java.util.*

object Generator {
    fun generate(bombnumber: Int, width: Int, height: Int): Array<IntArray> {
        // Random for generating numbers
        var bombnumber = bombnumber
        val r = Random()
        var grid =
            Array(width) { IntArray(height) }
        for (x in 0 until width) {
            grid[x] = IntArray(height)
        }

        // Setting bomb at random places in the grid
        while (bombnumber > 0) {
            val x: Int = r.nextInt(width)
            val y: Int = r.nextInt(height)

            // -1 is the bomb
            if (grid[x][y] != -1) {
                grid[x][y] = -1
                bombnumber--
            }
        }

        // Call function to calculate number  of rest of the cells based on nearby neighboring bombs
        grid = calculateNeigbours(grid, width, height)
        return grid
    }

    // Function tocalculate number  of cells based on nearby neighboring bombs
    private fun calculateNeigbours(
        grid: Array<IntArray>,
        width: Int,
        height: Int
    ): Array<IntArray> {
        for (x in 0 until width) {
            for (y in 0 until height) {
                grid[x][y] = getNeighbourNumber(grid, x, y, width, height)
            }
        }
        return grid
    }

    private fun getNeighbourNumber(grid: Array<IntArray>, x: Int, y: Int, width: Int, height: Int): Int {
        if (grid[x][y] == -1) {
            return -1
        }
        var count = 0
        if (isMineAt(grid, x - 1, y + 1, width, height)) count++ // top-left
        if (isMineAt(grid, x, y + 1, width, height)) count++ // top
        if (isMineAt(grid, x + 1, y + 1, width, height)) count++ // top-right
        if (isMineAt(grid, x - 1, y, width, height)) count++ // left
        if (isMineAt(grid, x + 1, y, width, height)) count++ // right
        if (isMineAt(grid, x - 1, y - 1, width, height)) count++ // bottom-left
        if (isMineAt(grid, x, y - 1, width, height)) count++ // bottom
        if (isMineAt(grid, x + 1, y - 1, width, height)) count++ // bottom-right
        return count
    }

    // Return whether a cell  contained mine or not
    private fun isMineAt(grid: Array<IntArray>, x: Int, y: Int, width: Int, height: Int): Boolean {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            if (grid[x][y] == -1) {
                return true
            }
        }
        return false
    }
}
