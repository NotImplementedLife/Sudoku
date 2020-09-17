package com.notimplementedlife.sudoku

import android.util.Log
import java.util.*

class PuzzleGenerator {
    companion object {
        val base = arrayOf(
            1, 2, 3, 4, 5, 6, 7, 8, 9,
            4, 5, 6, 7, 8, 9, 1, 2, 3,
            7, 8, 9, 1, 2, 3, 4, 5, 6,
            2, 3, 4, 5, 6, 7, 8, 9, 1,
            5, 6, 7, 8, 9, 1, 2, 3, 4,
            8, 9, 1, 2, 3, 4, 5, 6, 7,
            3, 4, 5, 6, 7, 8, 9, 1, 2,
            6, 7, 8, 9, 1, 2, 3, 4, 5,
            9, 1, 2, 3, 4, 5, 6, 7, 8
        )

        fun generateBoard(seed:Long=0) : Array<SudokuCell> {
            var gen=base.copyOf()
            var rand= Random(seed)
            for(i in 0..2) {
                swapColumns(gen, rand.nextInt(2), rand.nextInt(2))
                swapColumns(gen, 3 + rand.nextInt(2), 3 + rand.nextInt(2))
                swapColumns(gen, 6 + rand.nextInt(2), 6 + rand.nextInt(2))
                swapRows(gen, rand.nextInt(2), rand.nextInt(2))
                swapRows(gen, 3 + rand.nextInt(2), 3 + rand.nextInt(2))
                swapRows(gen, 6 + rand.nextInt(2), 6 + rand.nextInt(2))

                swapColumns3(gen, rand.nextInt(2), rand.nextInt(2))
                swapRows3(gen, rand.nextInt(2), rand.nextInt(2))
            }

            val result=Array<SudokuCell>(81) {i->SudokuCell(i/9,i%9,rand.nextBoolean(),gen[i]) }
            return result
        }

        private fun swapRows(arr:Array<Int>,r1:Int,r2:Int) {
            for (k in 0..8) {
                val vi=9*r1+k
                val vj=9*r2+k
                arr[vi]=arr[vj].also { arr[vj]=arr[vi] }
            }
        }

        private fun swapColumns(arr:Array<Int>,c1:Int,c2:Int) {
            for( k in 0..8) {
                val vi=9*k+c1
                val vj=9*k+c2
                arr[vi]=arr[vj].also { arr[vj]=arr[vi] }
            }
        }

        private fun swapRows3(arr:Array<Int>,r1:Int,r2:Int) {
            for (k in 0..2)
                swapRows(arr,3*r1+k,3*r2+k)
        }

        private fun swapColumns3(arr:Array<Int>,c1:Int,c2:Int) {
            for (k in 0..2)
                swapColumns(arr,3*c1+k,3*c2+k)
        }
    }
}