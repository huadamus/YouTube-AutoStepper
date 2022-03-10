package autostepper

import com.sapher.youtubedl.YoutubeDL
import com.sapher.youtubedl.YoutubeDLRequest
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL


object YoutubeDownloader {

    fun download(url: String, progressListener: ProgressListener) {
        val request = YoutubeDLRequest(url, INPUT_DIRECTORY)
        request.setOption("ignore-errors")
        request.setOption("extract-audio")
        request.setOption("retries", 3)
        request.setOption("audio-format", "mp3")
        YoutubeDL.execute(request) { progress, eta -> progressListener.onProgress(progress.toInt(), eta.toInt()) }
    }

    @JvmStatic
    fun downloadThumbnail(url: String, destination: String) {
        val properUrl = URL("http://img.youtube.com/vi/" + url.split("=").last() + "/0.jpg")
        val inputStream: InputStream = BufferedInputStream(properUrl.openStream())
        val out = ByteArrayOutputStream()
        val buf = ByteArray(1024)
        var n = 0
        while (-1 != inputStream.read(buf).also { n = it }) {
            out.write(buf, 0, n)
        }
        out.close()
        inputStream.close()
        val response = out.toByteArray()
        val fos = FileOutputStream(destination)
        fos.write(response)
        fos.close()
    }
}
