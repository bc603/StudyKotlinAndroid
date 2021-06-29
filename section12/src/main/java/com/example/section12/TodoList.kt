package com.example.section12

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

// backgroundTint - 그냥 색상
// ripple - ripple effect 눌렀을때 살짝 변했다가 다시 바뀌는 색상
// 안드로이드의 컴포넌트는 독립적으로 실행되어 직접 결합이 발생하지 않는 구조
// 인텐트는 컴포넌트를 실행하기 위해 시스템에 넘기는 정보
// 명시적 인텐트 : 실행하고자 하는 컴포넌트의 클래스명을 인텐트에 담는 방법
// Extra 데이타 : 컴포넌트를 실행하면서 데이터를 전달
// 결과 되돌리기 : 사용자가 뒤로가기 버튼을 누르지 않고도 코드에서 자동으로 화면이 되돌아오게 처리
// startActivityForResult() 함수를 이용
class TodoList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java)
            //startActivity(intent)
            startActivityForResult(intent, 10)
        }
    }
}