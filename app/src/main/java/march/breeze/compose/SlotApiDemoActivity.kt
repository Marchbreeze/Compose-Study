package march.breeze.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import march.breeze.compose.ui.theme.ComposeStudyTheme

class SlotApiDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStudyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

// 컴포저블 생성
@Composable
fun MainScreen() {
    var linearSelected by remember { mutableStateOf(true) }
    var imageSelected by remember { mutableStateOf(true) }
    val onLinearClick = { value: Boolean ->
        linearSelected = value
    }
    val onTitleClick = { value: Boolean ->
        imageSelected = value
    }
}

// 화면 컨텐츠를 표시하는 컴포저블 추가
@Composable
fun ScreenContent(
    linearSelected: Boolean,
    imageSelected: Boolean,
    onLinearClick: (Boolean) -> Unit,
    onTitleClick: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

    }
}