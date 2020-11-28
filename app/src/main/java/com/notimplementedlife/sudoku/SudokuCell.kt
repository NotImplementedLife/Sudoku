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

    /**
     * This method packs a SudokuCell into a byte array
     *
     * A sudoku cell is stored as a 16-bit number, where
     *  bit  0     -> fixed
     *  bit  1     -> notes
     *  bits 2-11  -> value
     *  bits 12-15 -> expected input
     *
     */
    @ExperimentalUnsignedTypes
    fun toByteArray() : ByteArray {
        val stream=ByteArrayOutputStream()
        var r:UShort = 0u
        if(isFixed) r=r or 0b01u
        if(isNote)  r=r or 0b10u
        r=r or (value shl 2).toUShort()
        r=r or (expectedInput shl 12).toUShort()
        with(FileUtils()) {
            stream.writeUShort(r)
        }
        return stream.toByteArray()
    }

    companion object {
        @ExperimentalUnsignedTypes
        fun create(row:Int, col:Int, cellcode:UShort) : SudokuCell {
            val cell=SudokuCell(row,col,
                cellcode and 0b01u==0b01u.toUShort(),
                ((cellcode.toUInt() shr 12) and 0xFu).toInt()
            )
            cell.isNote=cellcode and 0b10u==0b10u.toUShort()
            cell.value=((cellcode.toUInt() shr 2) and 0x3FFu).toInt()
            return cell
        }
    }
}