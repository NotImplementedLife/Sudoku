package com.notimplementedlife.sudoku

import android.content.Context
import java.io.ByteArrayOutputStream
import java.io.File

class FileUtils {
    companion object {
        fun writeLastPuzzle(context: Context, arr:Array<SudokuCell>) {
            context.openFileOutput("saved.last", Context.MODE_PRIVATE).use {
                for (i in 0..80) it.write(arr[i].toByteArray())
            }
        }
    }

    @ExperimentalUnsignedTypes
    fun ByteArrayOutputStream.writeUShort(u:UShort) {
        var tmp:Int=u.toInt()
        for (k in 0..1) {
            write(tmp)
            tmp = tmp shr 8
        }
    }

}