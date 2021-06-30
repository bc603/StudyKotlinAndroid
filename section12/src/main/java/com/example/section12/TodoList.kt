package com.example.section12

import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_header.view.*
import kotlinx.android.synthetic.main.item_main.view.*
import java.text.SimpleDateFormat
import java.util.*

// backgroundTint - 그냥 색상
// ripple - ripple effect 눌렀을때 살짝 변했다가 다시 바뀌는 색상
// 안드로이드의 컴포넌트는 독립적으로 실행되어 직접 결합이 발생하지 않는 구조
// 인텐트는 컴포넌트를 실행하기 위해 시스템에 넘기는 정보
// 명시적 인텐트 : 실행하고자 하는 컴포넌트의 클래스명을 인텐트에 담는 방법
// Extra 데이타 : 컴포넌트를 실행하면서 데이터를 전달
// 결과 되돌리기 : 사용자가 뒤로가기 버튼을 누르지 않고도 코드에서 자동으로 화면이 되돌아오게 처리
// startActivityForResult() 함수를 이용
class TodoList : AppCompatActivity() {

    var list : MutableList<ItemVO> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java)
            //startActivity(intent)
            startActivityForResult(intent, 10)
        }

        selectDB()
    }

    fun selectDB() {
        list = mutableListOf()
        val helper = DBHelper(this)
        val db = helper.writableDatabase
        val cursor = db.rawQuery("select * from tb_todo order by date desc", null)
        var predate:Calendar?=null
        while(cursor.moveToNext()) {
            val dbDate = cursor.getString(3)
            val date = SimpleDateFormat("yyyy-MM-dd").parse(dbDate)
            val currentDate = GregorianCalendar()
            currentDate.time = date
            if(!currentDate.equals(predate)) {
                val headerItem = HeaderItem(dbDate)
                list.add(headerItem)
                predate = currentDate
            }
            val completed = cursor.getInt(4) != 0
            val dataItem = DataItem(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                completed
            )
            list.add(dataItem)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter(list)
        recyclerView.addItemDecoration(MyDecoration())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == RESULT_OK) {
            selectDB()
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val headerView = view.itemHeaderView
    }
    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemTitleView = view.itemTitleView
        val itemContentView = view.itemContentView
    }

    inner class MyAdapter(val list: MutableList<ItemVO>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            if(viewType == ItemVO.TYPE_HEADER) {
                val inflater = LayoutInflater.from(parent.context)
                return HeaderViewHolder(inflater.inflate(R.layout.item_header, parent, false))
            } else {
                val inflater = LayoutInflater.from(parent.context)
                return DataViewHolder(inflater.inflate(R.layout.item_main, parent, false))
            }
        }

        // 항목을 구성하기 위해
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val itemVO = list.get(position)
            if(itemVO.type == ItemVO.TYPE_HEADER) {
                val viewHolder = holder as HeaderViewHolder
                val headerItem = itemVO as HeaderItem
                viewHolder.headerView.text = headerItem.date
            } else {
                val viewHolder = holder as DataViewHolder
                val dataItem = itemVO as DataItem
                viewHolder.itemTitleView.text = dataItem.title
                viewHolder.itemContentView.text = dataItem.content
            }
        }

        override fun getItemViewType(position: Int): Int {
            return list.get(position).type
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }

    inner class MyDecoration:RecyclerView.ItemDecoration() {
        //각각의 항목을 꾸밈
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            val index = parent!!.getChildAdapterPosition(view)
            val itemVO = list.get(index)
            if (itemVO.type == ItemVO.TYPE_DATA) {
                view!!.setBackgroundColor(0xFFFFFFFF.toInt())
                ViewCompat.setElevation(view, 10.0f)
            }

            outRect!!.set(20, 10, 20, 10)
        }
    }
}

abstract class ItemVO {
    abstract val type : Int
    companion object {
        val TYPE_HEADER = 0
        val TYPE_DATA = 1
    }
}

class HeaderItem(var date:String): ItemVO() {
    override val type: Int
        get() = ItemVO.TYPE_HEADER
}

class DataItem(var id: Int, var title:String, var content:String, var completed:Boolean = false)
    : ItemVO() {
    override val type: Int
        get() = ItemVO.TYPE_DATA
    }