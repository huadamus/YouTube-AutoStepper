package autostepper

import com.google.gson.Gson
import com.sapher.youtubedl.YoutubeDL
import com.sapher.youtubedl.YoutubeDLRequest
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.*
import java.net.URL
import javax.imageio.ImageIO


object YoutubeDownloader {

    fun download(url: String, data: Data, progressListener: ProgressListener) {
        val request = YoutubeDLRequest(url, INPUT_DIRECTORY)
        request.setOption("ignore-errors")
        request.setOption("extract-audio")
        request.setOption("retries", 3)
        request.setOption("audio-format", "mp3")
        val outputOption = "_.%(ext)s"
        request.setOption("output", outputOption)
        YoutubeDL.execute(request) { progress, eta -> progressListener.onProgress(progress.toInt(), eta.toInt()) }
    }

    @JvmStatic
    fun downloadThumbnail(url: String, destination: String) {
        val jpgDestination = destination.substring(0, destination.length - 4) + ".jpg"
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
        val fos = FileOutputStream(jpgDestination)
        fos.write(response)
        fos.close()

        val bufferGambar = ImageIO.read(File(jpgDestination))
        val newBufferGambar = BufferedImage(bufferGambar.width, bufferGambar.height, BufferedImage.TYPE_INT_RGB)
        newBufferGambar.createGraphics().drawImage(bufferGambar, 0, 0, Color.white, null)
        ImageIO.write(newBufferGambar, "png", File(destination))

    }

    fun downloadData(url: String): Data {
        val properUrl = URL("https://noembed.com/embed?url=$url")
        val inputStream: InputStream = BufferedInputStream(properUrl.openStream())
        val out = ByteArrayOutputStream()
        val buf = ByteArray(1024)
        var n = 0
        while (-1 != inputStream.read(buf).also { n = it }) {
            out.write(buf, 0, n)
        }
        out.close()
        inputStream.close()
        return Gson().fromJson(out.toString(), Data::class.java)
    }
}
