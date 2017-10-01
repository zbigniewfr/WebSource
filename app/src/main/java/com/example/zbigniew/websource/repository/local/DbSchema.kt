package com.example.zbigniew.websource.repository.local

object DbSchema {

    const private val DELIMITER = "__"

    object WebSource {
        const val TABLE = "web_source"

        const val ID = TABLE + DELIMITER + "id"
        const val SOURCE = TABLE + DELIMITER + "source"

        val CREATE_TABLE = """
        |CREATE TABLE ${TABLE} (
        | ${ID} NUMBER PRIMARY KEY,
        | ${SOURCE} TEXT)""".trimMargin()
    }

}
