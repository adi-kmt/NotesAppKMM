package com.adikmt.notesapp

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import com.adikmt.notesapp.di.initKoin
import com.adikmt.notesapp.ui.krouter.LocalComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.defaultComponentContext
import org.koin.android.ext.koin.androidContext

class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootComponentContext: DefaultComponentContext = defaultComponentContext()

        initKoin {
            androidContext(applicationContext)
        }


        setContent {
            CompositionLocalProvider(LocalComponentContext provides rootComponentContext) {
                App()
            }
        }
    }
}