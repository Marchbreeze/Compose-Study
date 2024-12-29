## 1. 내비게이션 컨트롤러

- NavHostController 인스턴스
    - 백스택을 관리하고 현재 목적지가 어떤 컴포저블인지 추적
    - 시작 목적지와 내비게이션 그래프 역할을 하는 컴포저블

    ```kotlin
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = <시작 경로>) {
    		
    }
    ```


- 내비게이션 그래프에 목적지 추가하기

    ```kotlin
    sealed class Routes(val route: String) {
    		object Home: Routes("home")
    		object Customers: Routes("customers")
    		object Purchases: Routes("purchases")
    }
    ```

    ```kotlin
    NavHost(navController = navController, startDestination = Routes.Home.route) {
    		composable(Route.Home.route) {
    				Home()
    		}
    		
    		composable(Route.Customers.route) {
    				Customers()
    		}
    		
    		composable(Route.Purchases.route) {
    				Purchases()
    		}
    }
    ```


## 2. 목적지 이동

- 이동
    - 목적지로 이동하기

        ```kotlin
        Button(onClick = {
        		navController.navigate(Routes.Customers.route)
        })
        ```

    - 아이템을 스택에서 꺼내고 특정 목적지로 돌아가기

        ```kotlin
        Button(onClick = {
        		navController.navigate(Routes.Customers.route) {
        				popUpTo(Routes.Home.route)
        		}
        })
        ```

        - inclusive, launchSingleTop 등의 옵션 사용 가능

- 목적지에 인수 전달
    - 인수 추가

        ```kotlin
        NavHost(navController = navController, startDestination = Routes.Home.route) {
        		composable(Route.Purchases.route + "/{customerName}") {
        				Purchases()
        		}
        		
        		...
        ```

        ```kotlin
        Button(onClick = {
        		navController.navigate(Routes.Customers.route + "/$selectedCustomer")
        })
        ```

    - 경로에 포함해 인수에 할당한 값은 해당하는 백 스택 항목 안에 저장

        ```kotlin
        composable(Route.Purchases.route + "/{customerName}") { backStackEntry ->
        		
        		val customerName = backStackEntry.arguments?.getString("customerName")
        		
        		Purchases(customerName)
        }
        ```

    - String이 아닌 경우

        ```kotlin
        composable(Route.Purchases.route + "/{customerId}",
        					 arguments = listOf(navArgument("customerId") {type = NavTypr.IntType})) { navBackStack ->
        		
        		Customers(navBackStack.arguments?.getInt("customerId"))
        }
        ```


- 예시

    ```kotlin
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
    ```

    ```kotlin
    // HomeScreen
    Button(onClick = {
        navController.navigate(NavRoutes.Welcome.route + "/$userName")
    }) {
        Text(text = "Register")
    }
    ```

    ```kotlin
    // WelcomeScreen
    Button(onClick = {
        navController.navigate(NavRoutes.Profile.route) {
            popUpTo(NavRoutes.Home.route)
        }
    }) {
        Text(text = "Set up your Profile")
    }
    ```


## 3. 바텀 네비게이션

1. 부모 BottomNavigationBar가 forEach 루프 포함
2. forEach 루프가 반복되며 자식 BottomNavigationItem을 형성
3. 각 자식에 표시할 라벨과 아이콘이 설정되면 onClick 핸들러로 목적이 이동 수행

    ```kotlin
    BottomNavigation {
    		val backStackEntry by navController.currentBackStackEntryAsState()
    		val currentRoute = backStackEntry?.destination?.route
    
    		NavBarItems.BarItems.forEach { navItem ->
    				BottomNavigationItem(
    						selected = currentRoute == navItem.route,
    						onClick = {
    								navController.navigate(navItem.route) {
    										popUpTo(navController.graph.findStartDestination().id) {
    												saveState = true
    										}
    										launchSingleTop = true
    										restoreState = true
    								}
    						},
    						icon = {},
    						label = {}
    				)
    		}
    }
    ```


- Material3에서는 NavigationBar로 변경됨

    ```kotlin
    @Composable
    fun BottomNavigationBar(navController: NavHostController) {
    
        NavigationBar {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route
    
            NavBarItems.BarItems.forEach { navItem ->
    
                NavigationBarItem(
                    selected = currentRoute == navItem.route,
                    onClick = {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
    
                    icon = {
                        Icon(
                            imageVector = navItem.image,
                            contentDescription = navItem.title
                        )
                    },
                    label = {
                        Text(text = navItem.title)
                    },
                )
            }
        }
    }
    ```