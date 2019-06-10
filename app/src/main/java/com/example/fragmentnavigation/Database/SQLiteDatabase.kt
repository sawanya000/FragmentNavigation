package com.example.fragmentnavigation.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteDatabase(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,1) {
    companion object{
        val DATABASE_NAME = "Data.db"
        val TABLE_NAME = "image_table"
        val COL_1 = "IMAGESMALL"
        val COL_2 = "IMAGELARGE"
        val COL_3 = "TIME"

        val TABLE_NAME_USER = "user_table"
        val COL_1_USERNAME = "NAMEUSER"
        val COL_2_PASSWORD = "PASSWORD"
    }
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME(IMAGESMALL STRING,IMAGELARGE STRING,TIME STRING)")
        p0?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME_USER(NAMEUSER STRING,PASSWORD STRING)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_USER")
    }

    fun insertData(imagesmall:String,imagelarge:String,time:String):Boolean?{
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_1,imagesmall)
        cv.put(COL_2,imagelarge)
        cv.put(COL_3,time)
        val res = db.insert(TABLE_NAME,null,cv)
        return !res.equals(-1)
    }

    fun getAllData(): Cursor {
        val db = this.writableDatabase
        return  db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }
    fun daleteData(id: String): Int? {
        val db = this.writableDatabase
        println("dddddddddddddddd"+db.delete(TABLE_NAME, "IMAGESMALL =? ", arrayOf(id)))
        return db.delete(TABLE_NAME, "IMAGESMALL =? ", arrayOf(id))
    }

    fun getDataUser(username:String):Cursor {
        val db = this.writableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME_USER WHERE NAMEUSER=? ", arrayOf(username) )
    }
    fun addUser(username:String,password:String):Boolean?{
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_1_USERNAME,username)
        cv.put(COL_2_PASSWORD,password)
        val res = db.insert(TABLE_NAME_USER,null,cv)
        return !res.equals(-1)


    }
    fun editPassword(username: String,password: String):Boolean{
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_1_USERNAME,username)
        cv.put(COL_2_PASSWORD,password)
        db.update("user_table",cv,"NAMEUSER=?", arrayOf(username))
        return true

    }
}