package autostepper

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


//TODO: The app has problems with non-English characters
fun main() {
    application {
        Window(onCloseRequest = ::exitApplication, title = "Youtube AutoStepper") {
            App()
        }
    }
}

fun download(url: String, progressListener: ProgressListener) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            FileManager.prepareDirectories()
            FileManager.clearDirectories()
            YoutubeDownloader.download(url, progressListener)
            //FileManager.adjustFilenames()
            progressListener.onSuccess()
            convert(url)
            FileManager.clearDirectories()
        } catch (e: Exception) {
            progressListener.onError(e.toString())
        }
    }
}

fun convert(url: String) {
    AutoStepper.run(url, INPUT_DIRECTORY, OUTPUT_DIRECTORY, arrayOf())
}
