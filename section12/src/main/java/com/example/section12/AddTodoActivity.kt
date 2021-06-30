package com.example.section12

import android.app.DatePickerDialog
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import kotlinx.android.synthetic.main.activity_add_todo.*
import java.text.SimpleDateFormat
import java.util.*

// SAM(Single Abstract Method) 기법 적용
/*
    btn.setOnClickListener {
    }
 */
class AddTodoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)

        val date = Date()
        val sdFormat = SimpleDateFormat("yyyy-MM-dd")
        addDateView.text = sdFormat.format(date)

        addDateView.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val dateDialog = DatePickerDialog(this, object: DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    addDateView.text = "$year-${month+1}-${dayOfMonth}"
                }
            }, year, month, day).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //액티비티에 메뉴를 적용하려면 자바코드에서 MenuInflater를 이용
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // onCreateOptionsMenu(Menu menu) 메뉴가 만들어 질때 최초 한번 호출
    // onPrepareOptionsMenu(Menu menu) 메뉴가 화면에 보일 때마다 반복 호출
    // 메뉴구성을 위한 함수
    // add(CharSequence title)
    // add(int groupId, int itemId, int order, int titleRes)
    // add(int groupId, int itemId, int order, CharSequence title)
    // setIcon(int iconRes)
    // setTitle, setVisible, setEnable
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_add) {
            if(addTitleEditView.text.toString() != null && addContentEditView.text.toString() != null) {
                val helper = DBHelper(this)
                val db = helper.writableDatabase

                val contentValues = ContentValues()
                contentValues.put("title", addTitleEditView.text.toString())
                contentValues.put("content", addContentEditView.text.toString())
                contentValues.put("date", addDateView.text.toString())
                contentValues.put("completed", 0)

                db.insert("tb_todo", null, contentValues)
                db.close()

                setResult(RESULT_OK)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}