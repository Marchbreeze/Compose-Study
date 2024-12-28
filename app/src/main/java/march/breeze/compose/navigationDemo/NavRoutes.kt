package march.breeze.compose.navigationDemo

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object Welcome : NavRoutes("welcome")
    object Profile : NavRoutes("profile")
}