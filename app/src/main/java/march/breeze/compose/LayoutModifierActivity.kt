package march.breeze.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import march.breeze.compose.ui.theme.ComposeStudyTheme
import kotlin.math.roundToInt

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
                    MainScreen7()
                }
            }
        }
    }
}

fun Modifier.exampleLayout(
    fraction: Float
) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)

    val x = -(placeable.width * fraction).roundToInt()

    layout(placeable.width, placeable.height) {
        placeable.placeRelative(x = x, y = 0)
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
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(120.dp, 80.dp)) {
        Column {
            ColorBox(
                modifier = Modifier
                    .exampleLayout(0f)
                    .background(Color.Blue)
            )
            ColorBox(
                modifier = Modifier
                    .exampleLayout(0.25f)
                    .background(Color.Gray)
            )
            ColorBox(
                modifier = Modifier
                    .exampleLayout(0.5f)
                    .background(Color.Green)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    ComposeStudyTheme {
        MainScreen7()
    }
}