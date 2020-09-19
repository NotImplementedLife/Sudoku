package com.notimplementedlife.sudoku

import androidx.annotation.TransitionRes
import java.io.ByteArrayOutputStream
import java.io.Serializable

class SudokuCell (_row : Int,_col : Int,fixed:Boolean = false,_expVal : Int = 0) : Serializable {
    val row : Int = _row
    val col : Int = _col
    val isFixed : Boolean = fixed
    var isNote : Boolean = false
    var value : Int = 0
    val expectedInput = _expVal

    fun toByteArray() : ByteArray {
        val stream=ByteArrayOutputStream()
        var stats: Int = 0
        if(isFixed) stats=stats or 0x01
        if(isNote)  stats=stats or 0x10
        stream.write(stats)
        stream.write(value)
        return stream.toByteArray()
    }
}