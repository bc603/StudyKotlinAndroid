package com.example.section11

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "memodb", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table tb_memo(_id integer primary key autoincrement, title, content )")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


}