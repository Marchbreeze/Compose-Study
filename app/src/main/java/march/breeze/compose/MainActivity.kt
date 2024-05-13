package march.breeze.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    // mutableStateOf 런타임 함수 호출 후 초기 상태값 전달 (현재 상태값 유지 위해 remember 키워드 활용)
    var textState = remember { mutableStateOf("Hello") }
    // MutableState 인스턴스의 value 프로퍼티를 읽고 설정해 상태를 업데이트
    val onTextChange = { text: String ->
        textState.value = text
    }
    // 현재 상태값을 TextField에 할당
    TextField(value = textState.value, onValueChange = onTextChange)

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeStudyTheme {
        DemoScreen()
    }
}