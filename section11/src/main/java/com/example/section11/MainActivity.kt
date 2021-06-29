package com.example.section11

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_main.view.*


// SQLite는 오픈소스로 만들어진 관계형 데이터베이스
// data/data/package_name/database에 DB파일 저
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = openOrCreateDatabase("memodb", Context.MODE_PRIVATE, null)
        //execSQL(sql:String) // select문이 아닌 나머지 SQL
        //rawQuery(sql:String, selecttionArgs:Array<String>): selectSQL 수행

        // db.execSQL("insert into tb_memo(title, content) value(?, ?)", arrayOf<String>("hello", "world"))
        // val cursor = db.rawQuery("select title, content from tb_memo order by _id desc limit 1", null)
        // moveToNext (순서상 다음행 선택)
        // moveToFirst, moveToPrevious()

        // SQLiteOpenHelper
        // 테이블 생성이나 스키마 변경 등의 작업
        // onCreate() 앱이 설치된 후 SQLiteOpenHelper가 최초로 이용되는 순간 한 번 호출
        // val helper = DBHelper(this)
        // val db = helper.writableDatabase

        // val values = ContentValues()
        // values.put("name", "kotlin")
        // values.put("phone", "01012344444")
        // db.insert("user_tb", null, values)

        // val c = db.query("user_tb", arrayOf("name", "phone"), "ID = ?"
        //                  , arrayOf("kotlin"), null, null, null)

        // RecyclerView
        // andoridx 라이브러리로 제공, 목록 화면 구성에 사용
        // 항목을 만드는 책임자 adapter, 배치는 Layoutmanger
        // Adapter : 항목을 구성
        // ViewHoler : 각 항목 구성 뷰의 재활용을 목적으로 ViewHolder 역할
        // LayoutManager : 항목의 배치
        // ItemDecoration : 항목 꾸미기
        // ItemAnaimation : 아이템이 추가, 제거, 정렬될때의 애니메이션 처리

        // ViewHolder는 항목을 구성하는 뷰를 가지는 혁할

        // RecylerView LayaoutManger
        // LinearLayoutManager, GridLayoutManger,

        // ItemDecoration
        // 항목을 다양하게 꾸미기 위해 사용
        // onDraw 항목을 배치하기 전에 호출
        // onDrawOver 모든 항목이 배치된 후에 호출
        // getItemOffsets  각 항목을 배치할 떄 호출

        addBtnView.setOnClickListener{
            val title=addTitleView.text.toString()
            val content = addContentView.text.toString()

            val helper = DBHelper(this)
            val db = helper.writableDatabase
            db.execSQL("insert into tb_memo(title, content) values(?, ?)",
                arrayOf<String>(title, content))

            val cursor = db.rawQuery("select title, content from tb_memo order by _id desc limit 1", null)

            while(cursor.moveToNext()) {
                resultView.text = "${cursor.getString(0)}, ${cursor.getString(1)}"
            }

            db.close()

            val list = mutableListOf<String>()
            for(i in 0..10) {
                list.add("Item $i")
            }
            recyclerView.adapter = MyAdapter(list)
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemTextVirew = itemView.itemTextView
    }

    class MyAdapter(val list:MutableList<String>): RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val text = list.get(position)
            holder.itemTextVirew.text = text
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }
}