package com.notimplementedlife.sudoku

import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    lateinit var abWrite : ActionButton
    lateinit var abNotes : ActionButton
    lateinit var abClear : ActionButton
    lateinit var abNoAct : ActionButton
    lateinit var numpad : LinearLayout

    lateinit var numButtons : Array<NumberButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        abWrite = ActionButton(findViewById(R.id.btn_write),true)
        abNotes = ActionButton(findViewById(R.id.btn_notes),true)
        abClear = ActionButton(findViewById(R.id.btn_clear),false)
        abNoAct = ActionButton(findViewById(R.id.btn_no_action),false)

        numpad=findViewById(R.id.numpad)

        numpad.visibility=View.INVISIBLE
        selectActionButton(abNoAct)

        numButtons=Array<NumberButton>(9) {it->
                NumberButton(findViewById(resources.getIdentifier("btn_num_${it+1}", "id", packageName)))
        }
    }

    var abSelected : ActionButton? = null

    fun selectActionButton(ab:ActionButton) {
        if(abSelected!=null) {
            abSelected!!.view.colorFilter=null
            if(abSelected!!.bindNumpad) {
                numpad.visibility= View.INVISIBLE;
            }
        }
        abSelected = ab
        abSelected!!.view.setColorFilter(ContextCompat.getColor(this,R.color.colorAccent), PorterDuff.Mode.SRC_ATOP)
        if(abSelected!!.bindNumpad) {
            numpad.visibility= View.VISIBLE;
        }
        else{
            selectNumberButton(null)
        }
    }

    var nbSelected : NumberButton? = null

    fun selectNumberButton(nb:NumberButton?) {
        if(nbSelected!=null) {
            nbSelected!!.view.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.colorButtonBackground))
            nbSelected!!.view.setTextColor(Color.parseColor("#000000"))
        }
        nbSelected=nb
        if(nbSelected!=null) {
            nbSelected!!.view.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent))
            nbSelected!!.view.setTextColor(Color.parseColor("#ffffff"))
        }
    }

    inner class ActionButton(btn: ImageButton,bindNum:Boolean=false) {
        var view : ImageButton = btn
        var bindNumpad : Boolean = bindNum
        init {
          view.setOnClickListener {
              selectActionButton(this)
          }
        }
    }

    inner class NumberButton(btn: Button) {
        var view : Button=btn
        init {
            view.setOnClickListener{
                selectNumberButton(this)
            }
        }
    }
}