package march.breeze.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import march.breeze.compose.ui.theme.ComposeStudyTheme

class RowColDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStudyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}

@Composable
fun TextCell(text: String, modifier: Modifier = Modifier) {
    val cellModifier = Modifier
        .padding(4.dp)
        .size(100.dp, 100.dp)
        .border(width = 4.dp, color = Color.Black)

    Text(
        text = text,
        cellModifier.then(modifier),
        fontSize = 70.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun MainScreen2() {
    Column {
        Row {
            Column {
                TextCell(text = "1")
                TextCell(text = "2")
                TextCell(text = "3")
            }
            Column {
                TextCell(text = "4")
                TextCell(text = "5")
                TextCell(text = "6")
            }
            Column {
                TextCell(text = "7")
                TextCell(text = "8")
            }
        }
        Row {
            TextCell(text = "9")
            TextCell(text = "10")
            TextCell(text = "11")
        }
    }
}

@Composable
fun MainScreen3() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.size(width = 400.dp, height = 200.dp)
    ) {
        TextCell(text = "1")
        TextCell(text = "2")
        TextCell(text = "3")
    }
}

@Composable
fun MainScreen4() {
    Row(
        modifier = Modifier.height(200.dp)
    ) {
        TextCell(text = "1", Modifier.align(Alignment.Top))
        TextCell(text = "2", Modifier.align(Alignment.CenterVertically))
        TextCell(text = "3", Modifier.align(Alignment.Bottom))
    }
}

@Composable
fun MainScreen5() {
    Row {
        Text(
            text = "Large Text\nMore Text",
            Modifier
                .alignBy(FirstBaseline)
                .background(Color.Green),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Small Text",
            Modifier
                .paddingFrom(
                    alignmentLine = FirstBaseline,
                    before = 80.dp,
                    after = 20.dp
                )
                .background(Color.Gray),
            fontSize = 32.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    ComposeStudyTheme {
        MainScreen5()
    }
}