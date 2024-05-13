package march.breeze.compose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import march.breeze.compose.ui.theme.ComposeStudyTheme

class CompLocalDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStudyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Composable1()
                }
            }
        }
    }
}

// 기기 모드에 따라 변경되는 color 상태 선언 (값 주기적으로 변경되지 않음)
val LocalColor = staticCompositionLocalOf { Color.Gray }

@Composable
fun Composable1() {
    var color = if (isSystemInDarkTheme()) {
        Color.Green
    } else {
        Color.Gray
    }
    Column {
        Composable2()

        CompositionLocalProvider(LocalColor provides color) {
            Composable3()
        }
    }
}

@Composable
fun Composable2() {
    Composable4()
}

@Composable
fun Composable3() {
    Composable5()
}

@Composable
fun Composable4() {
    Composable6()
}

@Composable
fun Composable5() {
    Composable7()
    Composable8()
}

@Composable
fun Composable6() {
    Text(text = "Composable 6")
}

@Composable
fun Composable7() {

}

@Composable
fun Composable8() {
    Text(text = "Composable 8", modifier = Modifier.background(LocalColor.current))
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeStudyTheme {
        Composable1()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DarkModePreview() {
    ComposeStudyTheme {
        Composable1()
    }
}