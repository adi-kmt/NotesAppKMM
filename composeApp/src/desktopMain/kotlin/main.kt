import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.adikmt.notesapp.App
import com.adikmt.notesapp.di.initKoin
import com.adikmt.notesapp.ui.krouter.LocalComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry

fun main() = application {

    initKoin()
    val lifecycle = LifecycleRegistry()

    val rootComponentContext = DefaultComponentContext(lifecycle = lifecycle)
    Window(
        title = "NotesApp",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        CompositionLocalProvider(LocalComponentContext provides rootComponentContext) {
            App()
        }
    }
}