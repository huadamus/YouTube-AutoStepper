package autostepper

interface ProgressListener {
    fun onProgress(progressPercentage: Int, etaInSeconds: Int)
    fun onSuccess()
    fun onError(error: String)
}
