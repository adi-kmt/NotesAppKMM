package com.adikmt.notesapp.data

import app.cash.sqldelight.db.SqlDriver
import org.koin.core.scope.Scope

expect fun Scope.createDriver(): SqlDriver
