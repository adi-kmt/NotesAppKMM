import com.adikmt.notesapp.App
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        BrowserViewportWindow("NotesApp") {
            App()
        }
    }
}
