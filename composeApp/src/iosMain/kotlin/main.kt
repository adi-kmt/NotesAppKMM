import androidx.compose.ui.window.ComposeUIViewController
import com.adikmt.notesapp.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }
}
