1. 상태와 이벤트 핸들러를 보유하는 컴포저블 생성 
    
    ```kotlin
    @Composable
    fun MainScreen() {
        var linearSelected by remember { mutableStateOf(true) }
        var imageSelected by remember { mutableStateOf(true) }
        val onLinearClick = { value: Boolean ->
            linearSelected = value
        }
        val onTitleClick = { value: Boolean ->
            imageSelected = value
        }
    }
    ```
    
2. 체크박스 컴포저블 추가
    
    ```kotlin
    @Composable
    fun CheckBoxes(
        linearSelected: Boolean,
        imageSelected: Boolean,
        onTitleClick: (Boolean) -> Unit,
        onLinearClick: (Boolean) -> Unit,
    ) {
        Row(
            Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = imageSelected, onCheckedChange = onTitleClick)
            Text(text = "Image Title")
            Spacer(modifier = Modifier.width(20.dp))
            Checkbox(checked = linearSelected, onCheckedChange = onLinearClick)
            Text(text = "Linear Progress")
        }
    }
    
    @Preview(showBackground = true)
    @Composable
    fun DemoPreview() {
        CheckBoxes(
            linearSelected = true,
            imageSelected = true,
            onTitleClick = {},
            onLinearClick = {},
        )
    }
    
    ```
    
    ![2024-05-14_16-34-31.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/6a49d4bc-4fa8-41f7-86f1-25d169c7371e/2024-05-14_16-34-31.jpg)
    
3. 화면 컨텐츠를 표시하는 컴포저블 추가
    
    ```kotlin
    @Composable
    fun ScreenContent(
        linearSelected: Boolean,
        imageSelected: Boolean,
        onLinearClick: (Boolean) -> Unit,
        onTitleClick: (Boolean) -> Unit,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
    
        }
    }
    ```
    
4. MainScreen 컴포저블 완료하기
    
    ```kotlin
    @Composable
    fun ScreenContent(
        linearSelected: Boolean,
        imageSelected: Boolean,
        onLinearClick: (Boolean) -> Unit,
        onTitleClick: (Boolean) -> Unit,
        titleContent: @Composable () -> Unit,
        progressContent: @Composable () -> Unit,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            titleContent()
            progressContent()
            CheckBoxes(
                linearSelected = linearSelected,
                imageSelected = imageSelected,
                onLinearClick = onLinearClick,
                onTitleClick = onTitleClick
            )
        }
    }
    ```
    
    ```kotlin
    @Composable
    fun MainScreen() {
        var linearSelected by remember { mutableStateOf(true) }
        var imageSelected by remember { mutableStateOf(true) }
        val onLinearClick = { value: Boolean ->
            linearSelected = value
        }
        val onTitleClick = { value: Boolean ->
            imageSelected = value
        }
    
        ScreenContent(
            linearSelected = linearSelected,
            imageSelected = imageSelected,
            onLinearClick = onLinearClick,
            onTitleClick = onTitleClick,
            titleContent = {
                if (imageSelected) {
                    Text("Selected")
                } else {
                    Text("Unselected")
                }
            },
            progressContent = {
                if (linearSelected) {
                    LinearProgressIndicator(Modifier.height(40.dp))
                } else {
                    CircularProgressIndicator(Modifier.size(200.dp), strokeWidth = 18.dp)
                }
            }
        )
    }
    ```
    
    ```kotlin
    class SlotApiDemoActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                ComposeStudyTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MainScreen()
                    }
                }
            }
        }
    }
    ```
    
    ```kotlin
    @Preview(showSystemUi = true)
    @Composable
    fun DemoPreview() {
        MainScreen()
    }
    
    ```
    

![2024-05-14_16-45-37.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/6101b5f0-98b1-419b-bf0a-4daee5765fd7/2024-05-14_16-45-37.jpg)
