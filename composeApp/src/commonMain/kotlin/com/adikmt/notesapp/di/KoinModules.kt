package com.adikmt.notesapp.di

import app.cash.sqldelight.db.SqlDriver
import com.adikmt.notesapp.NotesDB
import com.adikmt.notesapp.data.NoteLocalDataSource
import com.adikmt.notesapp.data.NotesLocalDataSourceImpl
import com.adikmt.notesapp.data.createDriver
import org.koin.dsl.module

val koinModule = module {

    single<SqlDriver> { createDriver() }
    single<NoteLocalDataSource> { NotesLocalDataSourceImpl(NotesDB(get())) }
}
