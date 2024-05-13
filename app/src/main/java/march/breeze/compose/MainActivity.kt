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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import march.breeze.compose.ui.theme.ComposeStudyTheme

class MainActivity : ComponentActivity() {
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

// MyTextField를 하나의 상태 변수, 그리고 입력에 따라 상태를 변경하는 하나의 이벤트 핸들러를 포함한 상태 컴포저블 함수로 만듬
@Composable
fun DemoScreen() {
    MyTextField()
}

@Composable
fun MyTextField() {
    // by 키워드로 프로퍼티 delegation
    var textState by remember { mutableStateOf("Hello") }
    // 이벤트 핸들러에서 프로퍼티 직접 참조하지 않고도 접근 가능
    val onTextChange = { text: String ->
        textState = text
    }
    // 현재 상태값을 TextField에 할당
    TextField(value = textState, onValueChange = onTextChange)
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