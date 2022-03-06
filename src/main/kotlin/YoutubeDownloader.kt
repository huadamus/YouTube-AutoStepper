import com.sapher.youtubedl.YoutubeDL
import com.sapher.youtubedl.YoutubeDLRequest


object YoutubeDownloader {

    fun download(url: String, progressListener: ProgressListener) {
        val request = YoutubeDLRequest(url, INPUT_DIRECTORY)
        request.setOption("ignore-errors")
        request.setOption("extract-audio")
        request.setOption("retries", 3)
        request.setOption("audio-format", "mp3")
        YoutubeDL.execute(request) { progress, eta -> progressListener.onProgress(progress.toInt(), eta.toInt()) }
    }
}
