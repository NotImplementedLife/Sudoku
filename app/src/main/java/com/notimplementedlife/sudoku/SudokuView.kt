package com.notimplementedlife.sudoku

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat


class SudokuView : View {
    var boardLinesColor : Int = ResourcesCompat.getColor(resources, R.color.holo_red_dark, null)

    var boardLinePaint0 : Paint
    var boardLinePaint1 : Paint

    var numberPaint0 : Paint
    var numberPaint1 : Paint

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

        numberPaint0 = Paint()
        numberPaint0.color = Color.BLACK
        numberPaint0.style = Paint.Style.FILL
        numberPaint0.textSize = 30f

        numberPaint1 = Paint()
        numberPaint1.color = Color.BLACK
        numberPaint1.style = Paint.Style.FILL
        numberPaint1.textSize = 10f
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

        numberPaint0.textSize = s9*0.75f
        numberPaint1.textSize = s9*0.3f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(board, 0f, 0f, null)
        canvas.translate(translateX,translateY)

        for(i in 0..80) {
            val cell=cells[i]
            val row = i / 9
            var col = i % 9
            if(!cell.isNote) {
                canvas.drawText(cell.value.toString(),col*s9+s9*0.3f,row*s9+s9*0.75f,numberPaint0)
            }
            else {
                var k=0
                var v=cell.value
                while(v>0) {
                    if(v%2==1) {
                        val r=(k-1)/3
                        val c=(k-1)%3
                        canvas.drawText(k.toString(),col*s9+0.08f*s9+c*0.3f*s9,row*s9+(r+1)*0.3f*s9,numberPaint1)
                    }
                    v/=2
                    k++
                }
            }
        }
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
            invalidate()
        }
        return false
    }

    var onCellTouch :((cell:SudokuCell)->Unit)? = null
}