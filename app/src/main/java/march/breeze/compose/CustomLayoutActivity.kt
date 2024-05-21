package march.breeze.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import march.breeze.compose.ui.theme.ComposeStudyTheme

class CustomLayoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStudyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen8()
                }
            }
        }
    }
}

@Composable
fun CascadeLayout(
    spacing: Int = 0,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        var indent = 0
        layout(constraints.maxWidth, constraints.maxHeight) {
            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }
            var yCoord = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(x = indent, y = yCoord)
                indent += placeable.width + spacing
                yCoord += placeable.height + spacing
            }
        }
    }
}

@Composable
fun MainScreen8() {
    Box {
        CascadeLayout(spacing = 20) {
            Box(modifier = Modifier.size(60.dp).background(Color.Blue))
            Box(modifier = Modifier.size(80.dp, 40.dp).background(Color.Green))
            Box(modifier = Modifier.size(70.dp, 100.dp).background(Color.Gray))
            Box(modifier = Modifier.size(50.dp).background(Color.Cyan))
            Box(modifier = Modifier.size(20.dp).background(Color.Magenta))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview6() {
    ComposeStudyTheme {
        MainScreen8()
    }
}