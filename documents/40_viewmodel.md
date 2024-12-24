## 1. 상태 기반 ViewModel

- 관찰 가능한 데이터를 컴포즈의 상태 메커니즘을 활용하는 방법

- 선언

    ```kotlin
    class MyViewModel(): ViewModel() {
    		var customerCount by mutableCountOf(0)
    		
    		fun increaseCount() {
    				customerCount ++
    		}
    }
    ```


- 연결

    ```kotlin
    // 액티비티
    @Composable
    fun TopLevel(model: MyViewModel = MyViewModel()) {
    		MainScreen(model.customerCount) { model.increaseCount() }
    }
    
    @Composable
    fun MainScreen(count: Int, addCount: () -> Unit = {}) {
    
    }
    ```


- viewModel() 함수는 컴포즈 뷰 모델 라이프사이클 라이브러리가 제공

    ```kotlin
    implementation 'androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1'
    ```

    ```kotlin
    lifecycle-viewmodel-compose = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-compose", version.ref = "lifecycle-viewmodel-compose" }
    ```


## 2. LiveData 활용

- 선언

    ```kotlin
    class MyViewModel(): ViewModel() {
    		var customerName: MutableLiveData<String> = MutableLiveData("")
    		
    		fun setName(name: String) {
    				customerName.value = name
    		}
    }
    ```


- 연결

    ```kotlin
    // 액티비티
    @Composable
    fun TopLevel(model: MyViewModel = MyViewModel()) {
    		var customerNameL String by model.customerName.observeAsState("")
    }
    ```


## 3. 예시 구현

- 뷰모델

    ```kotlin
    class DemoViewModel : ViewModel() {
        var isFahrenheit by mutableStateOf(true)
        var result by mutableStateOf("")
    
        fun convertTemp(temp: String) {
    
            try {
                val tempInt = temp.toInt()
    
                if (isFahrenheit) {
                    result = ((tempInt - 32) * 0.5556).roundToInt().toString()
                } else {
                    result = ((tempInt * 1.8) + 32).roundToInt().toString()
                }
            } catch (e: Exception) {
                result = "Invalid Entry"
            }
        }
    
        fun switchChange() {
            isFahrenheit = !isFahrenheit
        }
    }
    ```


- 액티비티

    ```kotlin
    class DemoActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                ComposeStudyTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        ScreenSetup()
                    }
                }
            }
        }
    }
    
    @Composable
    fun ScreenSetup(viewModel: DemoViewModel = DemoViewModel()) {
        MainScreen(
            isFahrenheit = viewModel.isFahrenheit,
            result = viewModel.result,
            convertTemp = { viewModel.convertTemp(it) },
            switchChange = { viewModel.switchChange() }
        )
    }
    
    @Composable
    fun MainScreen(
        isFahrenheit: Boolean,
        result: String,
        convertTemp: (String) -> Unit,
        switchChange: () -> Unit
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
    
            var textState by remember { mutableStateOf("") }
    
            val onTextChange = { text: String ->
                textState = text
            }
    
            Text(
                "Temperature Converter",
                modifier = Modifier.padding(20.dp),
                style = MaterialTheme.typography.bodyLarge
            )
    
            InputRow(
                isFahrenheit = isFahrenheit,
                textState = textState,
                switchChange = switchChange,
                onTextChange = onTextChange
            )
    
            Text(
                result,
                modifier = Modifier.padding(20.dp),
                style = MaterialTheme.typography.bodyMedium
            )
    
            Button(
                onClick = { convertTemp(textState) }
            )
            {
                Text("Convert Temperature")
            }
        }
    
    }
    
    @Composable
    fun InputRow(
        isFahrenheit: Boolean,
        textState: String,
        switchChange: () -> Unit,
        onTextChange: (String) -> Unit
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
    
            Switch(
                checked = isFahrenheit,
                onCheckedChange = { switchChange() }
            )
    
            OutlinedTextField(
                value = textState,
                onValueChange = { onTextChange(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                label = { Text("Enter temperature") },
                modifier = Modifier.padding(10.dp),
                textStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                ),
            )
    
            Crossfade(
                targetState = isFahrenheit,
                animationSpec = tween(2000), label = ""
            ) { visible ->
                when (visible) {
                    true -> Text("\u2109", style = MaterialTheme.typography.bodyLarge)
                    false -> Text("\u2103", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
    
    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun DefaultPreview(model: DemoViewModel = DemoViewModel()) {
        ComposeStudyTheme {
            MainScreen(
                isFahrenheit = model.isFahrenheit,
                result = model.result,
                convertTemp = { model.convertTemp(it) },
                switchChange = { model.switchChange() }
            )
        }
    }
    ```

  ![2024-12-25_01-20-34.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/5ae69c4c-4aab-446a-90e1-4eb147a2c7a8/2024-12-25_01-20-34.jpg)