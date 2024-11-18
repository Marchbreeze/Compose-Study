package march.breeze.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import march.breeze.compose.ui.theme.ComposeStudyTheme

class AnimationVisibilityActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStudyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen14()
                }
            }
        }
    }
}

@Composable
fun MainScreen14() {
    var boxVisible by remember { mutableStateOf(true) }

    val onClick = { newState: Boolean ->
        boxVisible = newState
    }

    Column(
        Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Crossfade(
                targetState = boxVisible,
                animationSpec = tween(5000)
            ) { visible ->
                when (visible) {
                    true -> CustomButton(
                        text = "Hide", targetState = false,
                        onClick = onClick, bgColor = Color.Red
                    )

                    false -> CustomButton(
                        text = "Show", targetState = true,
                        onClick = onClick, bgColor = Color.Magenta
                    )
                }
            }

        }
    }
}

@Composable
fun CustomButton(
    text: String, targetState: Boolean,
    onClick: (Boolean) -> Unit, bgColor: Color = Color.Blue
) {

    Button(
        onClick = { onClick(targetState) },
        colors = ButtonDefaults.buttonColors(
            bgColor,
            contentColor = Color.White
        )
    ) {
        Text(text)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview14() {
    ComposeStudyTheme {
        MainScreen14()
    }
}