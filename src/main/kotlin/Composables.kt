@file:Suppress("FunctionName")

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collection.mutableVectorOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import java.awt.Desktop
import java.io.File

@Composable
fun App() {
    var text by remember { mutableStateOf("") }
    var progressLabel by remember { mutableStateOf("") }
    var inProgress by remember { mutableStateOf(false) }

    MaterialTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            Text("Stepmania Youtube AutoStepper", fontSize = 30.sp)
            Text("App by Hubert Adamus. Original AutoStepper by Phr00t.")
            TextField(value = text, onValueChange = { text = it }, label = { Text("Youtube URL") })
            if(!inProgress) {
                Button(onClick = {
                    inProgress = true
                    download(text, object : ProgressListener {
                        override fun onProgress(progressPercentage: Int, etaInSeconds: Int) {
                            progressLabel = "Progress: $progressPercentage%, remaining time: ${etaInSeconds}s"
                        }

                        override fun onSuccess() {
                            progressLabel = "Success!"
                            inProgress = false
                        }

                        override fun onError(error: String) {
                            progressLabel =
                                "An error has occurred. Try again or use another video. Technical information: $error"
                            inProgress = false
                        }
                    })
                }) {
                    Text("Convert Youtube video")
                }
            } else {
                CircularProgressIndicator()
            }
            Text(progressLabel)
            Button(onClick = {
                Desktop.getDesktop().open(File(INPUT_DIRECTORY))
            }) {
                Text("Open folder")
            }
        }
    }
}
