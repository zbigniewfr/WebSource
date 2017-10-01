package com.example.zbigniew.websource.repository.local

import android.content.ContentValues
import com.example.zbigniew.websource.model.WebSource


abstract class ValuesProvider<out T> constructor(private val obj: T) {
    val cv: ContentValues = ContentValues()
    abstract fun convert()
    fun toContentValues(): ContentValues {
        convert()
        return cv
    }
}


class ReminderValueProvider(private val reminder: WebSource) : ValuesProvider<WebSource>(reminder) {
    override fun convert() {
        cv.put(DbSchema.WebSource.ID, reminder.id)
        cv.put(DbSchema.WebSource.SOURCE, reminder.source)
    }
}
