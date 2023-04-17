package com.adikmt.notesapp.data

import app.cash.sqldelight.db.SqlDriver
import org.koin.core.scope.Scope
import com.adikmt.notesapp.NotesDB
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

actual fun Scope.createDriver(): SqlDriver {
    val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    NotesDB.Schema.create(driver)
    return driver
}