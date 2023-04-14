import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import com.adikmt.notesapp.App
import com.adikmt.notesapp.di.initKoin
import com.adikmt.notesapp.ui.krouter.LocalComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {

    initKoin()
    val lifecycle = LifecycleRegistry()

    val rootComponentContext = DefaultComponentContext(lifecycle = lifecycle)

    return ComposeUIViewController {
        CompositionLocalProvider(LocalComponentContext provides rootComponentContext) {
            App()
        }
    }
}
