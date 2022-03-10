package autostepper

import java.io.File


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

    fun renameFiles() {
        val files = File(INPUT_DIRECTORY).listFiles()
        if (files != null) {
            for (f in files) {
                if (!f.isDirectory) {
                    val newFile = File(f.name.replace("_", " "))
                    f.renameTo(newFile)
                }
            }
        }
    }
}
