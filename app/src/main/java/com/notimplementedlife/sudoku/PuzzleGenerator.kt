package com.notimplementedlife.sudoku

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

        fun generate(seed:Int) : Array<SudokuCell> {
            return Array(0){i->SudokuCell(0,0) };
        }

        private fun swapLines(arr:Array<Int>,i:Int,j:Int) {
            for( k in 0..8) {
                val vi=9*i+k
                val vj=9*j+k
                arr[vi]=arr[vj].also { arr[vj]=arr[vi] }
            }
        }
    }
}