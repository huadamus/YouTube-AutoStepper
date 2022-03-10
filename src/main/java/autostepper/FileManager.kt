package autostepper

import java.io.File
import java.io.IOException

import java.nio.file.Files
import java.nio.file.Path


object FileManager {

    fun prepareDirectories() {
        File(INPUT_DIRECTORY).mkdirs()
    }

    fun clearDirectories() {
        val files = File(INPUT_DIRECTORY).listFiles()
        if (files != null) {
            for (f in files) {
                if (!f.isDirectory) {
                    f.delete()
                }
            }
        }
    }

    fun adjustFilenames() {
        val files = File(INPUT_DIRECTORY).listFiles()
        for (f in files) {
            if (!f.isDirectory) {
                //val newFile = File(INPUT_DIRECTORY + getAdjustedFilename(f.name))
                val source: Path = f.toPath()
                try {
                    Files.move(source, source.resolveSibling(INPUT_DIRECTORY + "f.mp3"))
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getAdjustedFilename(filename: String): String {
        val subNames = filename.split("-")
            .toMutableList()
        subNames.removeLast()
        var output = ""
        for (i in 0 until subNames.size) {
            output += subNames[i]
            if (i < subNames.size - 1) {
                output += "-"
            }
        }
        output += ".mp3"
        return output
    }
}
