package com.example.zbigniew.websource.repository.local

import android.database.sqlite.SQLiteDatabase
import com.example.zbigniew.websource.model.WebSource
import com.squareup.sqlbrite2.BriteDatabase
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable

class LocalDataSource(private val db: BriteDatabase) {

    fun saveWebSource(webSource: WebSource) {
        db.newTransaction().use { transactions ->
            db.insert(DbSchema.WebSource.TABLE, ReminderValueProvider(webSource).toContentValues(), SQLiteDatabase.CONFLICT_REPLACE)
            transactions.markSuccessful()
        }
    }

    fun deleteWebSource() {
        db.newTransaction().use { transactions ->
            db.delete(DbSchema.WebSource.TABLE, "${DbSchema.WebSource.ID} = (SELECT MAX(${DbSchema.WebSource.ID})  FROM ${DbSchema.WebSource.TABLE})")
            transactions.markSuccessful()
        }
    }

    fun getLastRecord(): Flowable<WebSource> {
        return db.createQuery(DbSchema.WebSource.TABLE, selectWebsources())
                .mapToList(DatabaseMapper().WebSourceDatabaseMapper().MAPPER)
                .toFlowable(BackpressureStrategy.BUFFER)
                .map {
                    if (it.size > 0) {
                        it[it.lastIndex]
                    } else {
                        WebSource(0, "Empty Database")
                    }
                }
    }


}