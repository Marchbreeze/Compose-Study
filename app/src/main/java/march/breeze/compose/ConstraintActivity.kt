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
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
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
    ConstraintLayout(Modifier.size(400.dp, 200.dp)) {
        val (button1, button2, button3) = createRefs()

        createHorizontalChain(button1, button2, button3)
        MyButton(text = "Button1", Modifier.constrainAs(button1) {
            centerVerticallyTo(parent)
        })

        val barrier = createBottomBarrier(button1, button3)

        MyButton(text = "Button2", Modifier.constrainAs(button2) {
            linkTo(button3.bottom, parent.bottom)
            height = Dimension.fillToConstraints
            top.linkTo(barrier)
        })

        val guide = createGuidelineFromTop(fraction = 0.2f)

        MyButton(text = "Button3", Modifier.constrainAs(button3) {
            top.linkTo(guide)
        })
    }
}

private fun myConstraintSet(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button1 = createRefFor("button1")
        constrain(button1) {
            linkTo(
                parent.top, parent.bottom,
                topMargin = margin, bottomMargin = margin
            )
            linkTo(
                parent.start, parent.end,
                startMargin = margin, endMargin = margin
            )
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }
    }
}

@Composable
fun MainScreen10() {
    ConstraintLayout(Modifier.size(200.dp, 200.dp)) {
        MyButton(text = "Button1", Modifier.size(200.dp).layoutId("button1"))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview7() {
    ComposeStudyTheme {
        MainScreen10()
    }
}