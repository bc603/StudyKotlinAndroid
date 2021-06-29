package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.* //Layout XML을 import

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 레아아웃 XML로 화면 구성
        setContentView(R.layout.activity_main)

        //레이아웃 xml파일 자체를 만들지 않고 코트린 코드로 모든 뷰를 직젖
        //생성하며, 메서드를 이용하여 뷰 설정을 일일이 지정

        /*val linearLayout = LinearLayout(this)

        val button1 = Button(this)
        button1.text = "Button1"
        linearLayout.addView(button1)

        val button2 = Button(this)
        button2.text = "Button2"
        linearLayout.addView(button2)

        setContentView(linearLayout)*/

        //ID속성
        //뷰의 식별자 속성, 필수 속성은 아니며 필요할 때 추가
        //지정한 id값은 R.java파일에 등록
        //xml에서 등록한 id값을 매개변수로 하여 findViewById()로 검색
        //val textView = findViewById<View>(R.id.textview1) as TextView

        //Kotlin Android Extension이란
        //findViewById() 함수를 이용하지 않고 쉽게 레이아웃 XML파일에 등록된 View 객체 획득
        //확장 플러그인 형태로 제공
        textview1.text = "Hmm HwaHwaHwaHwa"

        //Single Abstract Method 이용
        //자바에 선언된 인터페이스를 쉽게 이용하게 하는 코틀린 기법
        //추상 메서드가 하나인 인터페이스인 경우 적용 가능
        // 전통적인 방식
        /*button1.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                println("Gogogogogogog")
            }
        })*/

        button1.setOnClickListener {
            println("Button Click Single Abstract Method")
        }
    }
}