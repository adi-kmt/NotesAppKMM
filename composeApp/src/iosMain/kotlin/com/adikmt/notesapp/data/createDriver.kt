package com.adikmt.notesapp.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.adikmt.notesapp.NotesDB
import org.koin.core.scope.Scope

actual fun Scope.createDriver(): SqlDriver {
    return NativeSqliteDriver(NotesDB.Schema, "note.db")
}