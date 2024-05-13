package march.breeze.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import march.breeze.compose.ui.theme.ComposeStudyTheme

class StateExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStudyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DemoScreen()
                }
            }
        }
    }
}

@Composable
fun DemoScreen() {
    var textState by rememberSaveable { mutableStateOf("Hello") }
    val onTextChange = { text: String ->
        textState = text
    }
    MyTextField(text = textState, onTextChange = onTextChange)
}

@Composable
fun MyTextField(text: String, onTextChange: (String) -> Unit) {
    TextField(value = text, onValueChange = onTextChange)
}

@Composable
fun FunctionA() {
    var switchState by remember { mutableStateOf(true) }
    var onSwitchChange = { value: Boolean ->
        switchState = value
    }
    FunctionB(switchState = switchState, onSwitchChange = onSwitchChange)
}

@Composable
fun FunctionB(switchState: Boolean, onSwitchChange: (Boolean) -> Unit) {
    Switch(checked = switchState, onCheckedChange = onSwitchChange)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeStudyTheme {
        DemoScreen()
        FunctionA()
    }
}