package com.notimplementedlife.sudoku

class SudokuCell (_row : Int,_col : Int,fixed:Boolean = false) {
    val row : Int = _row
    val col : Int = _col
    val isFixed : Boolean = fixed
    var isNote : Boolean = false
    var value : Int = 0 // empty
}