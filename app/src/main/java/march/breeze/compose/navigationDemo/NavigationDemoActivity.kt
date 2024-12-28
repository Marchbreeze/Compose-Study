package march.breeze.compose.navigationDemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import march.breeze.compose.ui.theme.ComposeStudyTheme

class NavigationDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStudyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DemoScreen1()
                }
            }
        }
    }
}

@Composable
fun DemoScreen1() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route,
    ) {
        composable(NavRoutes.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(NavRoutes.Welcome.route + "/{userName}") { backStackEntry ->

            val userName = backStackEntry.arguments?.getString("userName")
            WelcomeScreen(navController = navController, userName)
        }

        composable(NavRoutes.Profile.route) {
            ProfileScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview1() {
    ComposeStudyTheme {
        DemoScreen1()
    }
}