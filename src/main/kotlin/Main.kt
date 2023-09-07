import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.json.JSONException
import org.skyscreamer.jsonassert.JSONCompare
import org.skyscreamer.jsonassert.JSONCompareMode

@Composable
@Preview
fun App() {
    var result by remember { mutableStateOf("") }
    var json1 by remember { mutableStateOf("") }
    var json2 by remember { mutableStateOf("") }
    val padding = 16.dp

    MaterialTheme {
        Column(
                modifier = Modifier.padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(Modifier.padding(padding)) {
                OutlinedTextField(
                        value = json1,
                        onValueChange = { json1 = it },
                        label = { Text("JSON 1") },
                        modifier = Modifier.fillMaxWidth(0.5F).fillMaxHeight(0.75F)

                )
                Spacer(Modifier.size(padding))
                OutlinedTextField(
                        value = json2,
                        onValueChange = { json2 = it },
                        label = { Text("JSON 2") },
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.75F)
                )
            }
            Row {
                Button(onClick = {
                    result = compareJSON(json1, json2)
                }) {
                    Text("Compare")
                }
            }
            if (result.isNotBlank()) {
                Row {
                    OutlinedTextField(
                            value = result,
                            readOnly = true,
                            onValueChange = {},
                            label = { Text("Result") },
                            modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

fun compareJSON(json1: String, json2: String): String {
    return try {
        val comparison = JSONCompare.compareJSON(json1, json2, JSONCompareMode.STRICT)
        if (comparison.passed()) "✅ Same JSON" else "❗ Differences :\n ${comparison.message}"
    } catch (e: JSONException) {
        "❌ Format Error"
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "JSON comparator") {
        App()
    }
}
