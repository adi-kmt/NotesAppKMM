package com.adikmt.notesapp.data

import app.cash.sqldelight.db.SqlDriver
import org.koin.core.scope.Scope
import app.cash.sqldelight.driver.sqljs.JsSqlDriver

actual fun Scope.createDriver(): SqlDriver {
    return JsSqlDriver(NotesDB.Schema)
}