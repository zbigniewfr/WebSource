package com.example.zbigniew.websource.repository.local

import android.database.Cursor
import com.example.zbigniew.websource.model.WebSource
import io.reactivex.functions.Function

class DatabaseMapper {
    val BOOLEAN_FALSE = 0
    val BOOLEAN_TRUE = 1

    fun getString(cursor: Cursor, columnName: String): String {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName))
    }

    fun getBoolean(cursor: Cursor, columnName: String): Boolean {
        return getInt(cursor, columnName) == BOOLEAN_TRUE
    }

    fun getLong(cursor: Cursor, columnName: String): Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(columnName))
    }

    fun getInt(cursor: Cursor, columnName: String): Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName))
    }

    private fun Db() {
        throw AssertionError("No instances.")
    }

    inner class WebSourceDatabaseMapper {
        var MAPPER: Function<Cursor, WebSource> = Function<Cursor, WebSource> { cursor ->
            val id = getInt(cursor, DbSchema.WebSource.ID)
            val title = getString(cursor, DbSchema.WebSource.SOURCE)
            WebSource(id, title)
        }
    }
}