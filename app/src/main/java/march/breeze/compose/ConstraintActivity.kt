package march.breeze.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import march.breeze.compose.ui.theme.ComposeStudyTheme

class ConstraintActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStudyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen9()
                }
            }
        }
    }
}

@Composable
fun MyButton(text: String, modifier: Modifier = Modifier) {
    Button(
        onClick = { },
        modifier = modifier
    ) {
        Text(text = text)
    }
}

@Composable
fun MainScreen9() {
    ConstraintLayout(Modifier.size(200.dp, 200.dp)) {
        val (button1, button2, button3) = createRefs()
        MyButton(text = "Button1", Modifier.constrainAs(button1) {
            top.linkTo(parent.top, margin = 60.dp)
            start.linkTo(parent.start, margin = 30.dp)
        })
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview7() {
    ComposeStudyTheme {
        MainScreen9()
    }
}