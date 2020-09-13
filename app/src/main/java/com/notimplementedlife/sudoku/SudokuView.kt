package com.notimplementedlife.sudoku

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat


class SudokuView : View {
    var boardLinesColor : Int = ResourcesCompat.getColor(resources, R.color.holo_red_dark, null)

    lateinit var boardLinePaint0 : Paint
    lateinit var boardLinePaint1 : Paint

    //constructor(context: Context) : this(context,null) { }
    constructor(context :Context, attributeSet : AttributeSet?) : super(context,attributeSet) {
        val backgroundColor: Int

        boardLinePaint0 = Paint()
        boardLinePaint0.color= boardLinesColor
        boardLinePaint0.isDither=true
        boardLinePaint0.style=Paint.Style.STROKE
        boardLinePaint0.strokeJoin = Paint.Join.ROUND
        boardLinePaint0.strokeCap = Paint.Cap.ROUND
        boardLinePaint0.strokeWidth = 15F

        boardLinePaint1 = Paint()
        boardLinePaint1.color= boardLinesColor
        boardLinePaint1.isDither=true
        boardLinePaint1.style=Paint.Style.STROKE
        boardLinePaint1.strokeJoin = Paint.Join.ROUND
        boardLinePaint1.strokeCap = Paint.Cap.ROUND
        boardLinePaint1.strokeWidth = 6F

    }

    lateinit var board : Bitmap
    private var translateX : Float =0f
    private var translateY : Float =0f
    private var s9 : Float = 1f

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        board = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(board)
        var s:Float=(1f*minOf(width,height))-25
        Toast.makeText(context,"$width $height $s",Toast.LENGTH_LONG).show();
        translateX=(width-s)*0.5f-6
        translateY=(height-s)*0.5f
        canvas.translate(translateX,translateY)
        s9 = s/9F;

        for(r in 0..9) {
            val hline=Path()
            hline.moveTo(0f,r*s9)
            hline.lineTo(s,r*s9)
            canvas.drawPath(hline,if(r%3==0) boardLinePaint0 else boardLinePaint1)
        }

        for(c in 0..9) {
            val vline=Path()
            vline.moveTo(c*s9,0f)
            vline.lineTo(c*s9,s)
            canvas.drawPath(vline,if(c%3==0) boardLinePaint0 else boardLinePaint1)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(board, 0f, 0f, null)
    }

    val cells : Array<SudokuCell> = Array(81) {i -> SudokuCell(i / 9,i % 9) }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val location = IntArray(2)
            getLocationOnScreen(location)
            val absLeft = location[0]
            val absTop = location[1]
            val col=((event.rawX-absLeft-translateX)/s9).toInt()
            val row=((event.rawY-absTop-translateY)/s9).toInt()
            if(row<0 || row>8 || col<0 || col>8) return false
            onCellTouch?.invoke(cells[9 * row + col])
        }
        return false
    }

    var onCellTouch :((cell:SudokuCell)->Unit)? = null
}