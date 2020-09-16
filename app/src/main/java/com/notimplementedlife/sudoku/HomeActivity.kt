package com.notimplementedlife.sudoku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        findViewById<Button>(R.id.btn_new_puzzle).setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java).apply{})
        };
    }
}