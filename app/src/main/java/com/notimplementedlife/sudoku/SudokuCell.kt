package com.notimplementedlife.sudoku

import androidx.annotation.TransitionRes
import java.io.Serializable

class SudokuCell (_row : Int,_col : Int,fixed:Boolean = false,_expVal : Int = 0) : Serializable {
    val row : Int = _row
    val col : Int = _col
    val isFixed : Boolean = fixed
    var isNote : Boolean = false
    var value : Int = 0
    val expectedInput = _expVal
}