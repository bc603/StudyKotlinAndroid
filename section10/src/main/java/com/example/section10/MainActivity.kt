package com.example.section10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/*
        val btn2: Button = findViewById(R.id.btnVisibleFalse)
        val btn1: Button = findViewById(R.id.btnVisibleTrue)
        val text: TextView = findViewById(R.id.textVisibleText)

        btn1.setOnClickListener{
            text.visibility = View.VISIBLE
        }

        btn2.setOnClickListener{ //it을 사용하면 btn2에 접근한다
            text.visibility = View.INVISIBLE
        }*/

        // Kotlin Andorid Extensions 기법 사용
        btnVisibleTrue.setOnClickListener{
            textVisibleText.visibility = View.VISIBLE
        }

        btnVisibleFalse.setOnClickListener{ //it을 사용하면 btn2에 접근한다
            textVisibleText.visibility = View.INVISIBLE
        }
    }
}