package com.example.fragmentnavigation.Database

import android.content.Context

class DB {
    lateinit var myDb: SQLiteDatabase
    fun initDB(context: Context){
        myDb = SQLiteDatabase(context)
    }

    fun getDB():SQLiteDatabase  {
        return myDb
    }
}