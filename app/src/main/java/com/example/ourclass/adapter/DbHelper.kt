package com.example.ourclass.adapter

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {
    companion object {
        val DATABASE_VERSION: Int = 1
        val DATABASE_NAME: String = "Ourclass.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        var sql = "create table checking(using_info text);"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS checking")
        onCreate(db)
    }

    fun checkingDataGet(): Cursor{
        val res: Cursor = this.readableDatabase.rawQuery("select * from checking", null)
        return res
    }
    fun checkingDataInsert(){
        val value = ContentValues().apply {
            put("using_info","signed")
        }
        this.writableDatabase.insertOrThrow("checking",null,value)
    }

}