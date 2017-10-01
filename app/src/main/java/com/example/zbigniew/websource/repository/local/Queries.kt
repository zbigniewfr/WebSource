package com.example.zbigniew.websource.repository.local


fun selectWebsources(): String {
    return """
    |SELECT * FROM ${DbSchema.WebSource.TABLE}
    """.trimMargin()
}