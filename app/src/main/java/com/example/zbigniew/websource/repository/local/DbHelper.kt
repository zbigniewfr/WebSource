package com.example.zbigniew.websource.repository.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper constructor(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "websource_db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DbSchema.WebSource.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS ${DbSchema.WebSource.TABLE}")
            onCreate(db)
    }

}