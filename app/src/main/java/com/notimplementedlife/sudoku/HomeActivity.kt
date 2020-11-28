package com.notimplementedlife.sudoku

import android.animation.LayoutTransition
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        findViewById<Button>(R.id.btn_new_puzzle).setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            val board=PuzzleGenerator.generateBoard()
            intent.putExtra("puzzle",board)
            startActivity(intent)
        }

        val continueButton = findViewById<Button>(R.id.btn_continue);
        continueButton.isEnabled= File(this.filesDir,"saved.last").exists();

        continueButton.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            val board=PuzzleGenerator.generateBoard()
            intent.putExtra("puzzle",board)
            startActivity(intent)
        }

        val levelsButton =findViewById<Button>(R.id.btn_levels)
        val levelsLayout =findViewById<LinearLayout>(R.id.levelsLayout)

        levelsButton.setOnClickListener {
            //levelsButton.layoutParams= LinearLayout.LayoutParams(100,LinearLayout.LayoutParams.WRAP_CONTENT)
            val layoutParams = levelsButton.getLayoutParams()
            if(layoutParams.width==LinearLayout.LayoutParams.MATCH_PARENT) {
                layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
                levelsButton.layoutParams = layoutParams
                levelsLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
                levelsButton.isSelected=true
            } else {
                layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
                levelsButton.layoutParams = layoutParams
                levelsLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
                levelsButton.isSelected=false
            }
        }
    }
}