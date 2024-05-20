package march.breeze.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import march.breeze.compose.ui.theme.ComposeStudyTheme

class LayoutModifierActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStudyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen6()
                }
            }
        }
    }
}

@Composable
fun ColorBox(modifier: Modifier) {
    Box(
        modifier = Modifier
            .padding(1.dp)
            .size(50.dp, 10.dp)
            .then(modifier)
    )
}

@Composable
fun MainScreen7() {
    Box(modifier = Modifier.size(120.dp, 80.dp)) {
        ColorBox(modifier = Modifier.background(Color.Blue))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    ComposeStudyTheme {
        MainScreen6()
    }
}