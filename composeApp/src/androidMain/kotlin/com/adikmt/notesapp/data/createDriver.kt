package com.adikmt.notesapp.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope

actual fun Scope.createDriver(): SqlDriver {
    return AndroidSqliteDriver(NotesDB.Schema, androidContext(), "note.db")
}