package autostepper

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
            val data = YoutubeDownloader.downloadData(url)
            YoutubeDownloader.download(url, data, progressListener)
            progressListener.onSuccess()
            convert(data, url)
            FileManager.clearDirectories()
        } catch (e: Exception) {
            progressListener.onError(e.toString())
        }
    }
}

fun convert(songData: Data, url: String) {
    AutoStepper.run(songData, url, INPUT_DIRECTORY, OUTPUT_DIRECTORY, arrayOf())
}
