## 1. 컴포즈 제스처 식별

- 두가지 제스터 감지 방법 제공
    1. 제스처 모디파이어 : 내장 시각 효과를 이용한 식별 기능
    2. PointerInputScope 인터페이스가 제공하는 함수 활용

### (1) 클릭 제스처

1. clickable 모디파이어로 모든 보이는 컴포저블에서 감지할 수 있음

    ```kotlin
    @Composable
    fun ClickDemo() {
    
        var colorState by remember { mutableStateOf(true)}
        var bgColor by remember { mutableStateOf(Color.Blue) }
    
        val clickHandler = {
            colorState = !colorState
            if (colorState == true) {
                bgColor = Color.Blue
            }
            else {
                bgColor = Color.DarkGray
            }
        }
    
        Box(
            Modifier
                .clickable { clickHandler() }
                .background(bgColor)
                .size(100.dp)
        )
    }
    ```


1. PointerInputScope의 detectTapGestures() : 탭, 프레스, 롱 프레스, 더블 탭 등을 구별할 수 있음

    ```kotlin
    @Composable
    fun TapPressDemo() {
    
        var textState by remember { mutableStateOf("Waiting ....") }
        val tapHandler = { status : String -> textState = status }
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                Modifier
                    .padding(10.dp)
                    .background(Color.Blue)
                    .size(100.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = { tapHandler("onPress Detected") },
                            onDoubleTap = { tapHandler("onDoubleTap Detected") },
                            onLongPress = { tapHandler("onLongPress Detected") },
                            onTap = { tapHandler("onTap Detected") }
                        )
                    }
            )
            Spacer(Modifier.height(10.dp))
            Text(textState)
        }
    }
    ```


### (2) 드래그 제스처

1. dragable 모디파이어
    - 움직임이 시작된 위치로부터의 오프셋을 상태로 저장 - rememberDragableState()
    - 이 상태를 이용해 이후 제스처의 좌표로 드래그된 컴포넌트의 위치를 변화시킴

        ```kotlin
        @Composable
        fun DragDemo() {
        
            Box(modifier = Modifier.fillMaxSize()) {
        
                var xOffset by remember { mutableStateOf(0f) }
        
                Box(
                    modifier = Modifier
                        .offset { IntOffset(xOffset.roundToInt(), 0) }
                        .size(100.dp)
                        .background(Color.Blue)
                        .draggable(
                            orientation = Orientation.Horizontal,
                            state = rememberDraggableState { distance ->
                                xOffset += distance
                            }
                        )
                )
            }
        }
        ```


1. PointerInputScope의 detectDragGestures()
    - 수평, 수직이 아닌 여러 방향으로의 드래그 조작 지원

        ```kotlin
        @Composable
        fun PointerInputDrag() {
        
            Box(modifier = Modifier.fillMaxSize()) {
        
                var xOffset by remember { mutableStateOf(0f) }
                var yOffset by remember { mutableStateOf(0f) }
        
                Box(
                    Modifier
                        .offset { IntOffset(xOffset.roundToInt(), yOffset.roundToInt()) }
                        .background(Color.Blue)
                        .size(100.dp)
                        .pointerInput(Unit) {
                            detectDragGestures { _, distance ->
                                xOffset += distance.x
                                yOffset += distance.y
                            }
                        }
                )
            }
        }
        ```


### (3) 스크롤 제스처

- scrollable 모디파이어
    - 리스트 컴포넌트 외에도 수평, 수직 스크롤 제스처를 적용할 수 있음
    - rememberScrollableState() 함수로 상태를 관리하고, 람다를 이용해 이동하는 거리에 접근할 수 있음

        ```kotlin
        @Composable
        fun ScrollableModifier() {
        
            var offset by remember { mutableStateOf(0f) }
        
            Box(
                Modifier
                    .fillMaxSize()
                    .scrollable(
                        orientation = Orientation.Vertical,
                        state = rememberScrollableState { distance ->
                            offset += distance
                            distance
                        }
                    )
            ) {
                Box(modifier = Modifier
                    .size(90.dp)
                    .offset { IntOffset(0, offset.roundToInt()) }
                    .background(Color.Red))
            }
        }
        ```

    - 수평 및 수직 스크롤 모두 감지 & 스크롤 처리

        ```kotlin
        @Composable
        fun ScrollModifiers() {
        
            val image = ImageBitmap.imageResource(id = R.drawable.vacation)
        
            Box(modifier = Modifier
                .size(150.dp)
                .verticalScroll(rememberScrollState())
                .horizontalScroll(rememberScrollState())) {
                Canvas(
                    modifier = Modifier
                        .size(360.dp, 270.dp)
                )
                {
                    drawImage(
                        image = image,
                        topLeft = Offset(
                            x = 0f,
                            y = 0f
                        ),
                    )
                }
            }
        }
        ```


### (4) 멀티 터치 제스처

- transformable() 모디파이어를 통해 꼬집기, 회전, 변환 제스처 감지
    - TransformableState 타입의 상태를 파라미터로 전달
    - 후행 람다는 3개의 파라미터 전달받음
        1. scaleChange: 꼬집기 제스처가 수행될 때 업데이트
        2. offsetChange: 대상 컴포넌트가 이동할 때 업데이트
        3. rotationChange: 회전 제스처를 감지했을 때 각도를 나타냄

    ```kotlin
    @Composable
    fun MultiTouchDemo() {
    
        var scale by remember { mutableStateOf(1f) }
        var angle by remember { mutableStateOf(0f) }
        var offset by remember { mutableStateOf(Offset.Zero)}
    
        val state = rememberTransformableState { scaleChange, offsetChange, rotationChange ->
            scale *= scaleChange
            angle += rotationChange
            offset += offsetChange
        }
    
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Box(
                Modifier
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        rotationZ = angle,
                        translationX = offset.x,
                        translationY = offset.y
    
                    )
                    .transformable(state = state)
                    .background(Color.Blue)
                    .size(100.dp)
            )
        }
    }
    ```


### (5) 스와이프 제스처

- 스와이프 제스처
    - 기기 화면에 접촉한 지점에서의 수형, 수직 움직임
    - 앵커 : 스와이프 축을 따라 화면에 존재하는 고정된 위치 → 컴포넌트를 한 앵커에서 다른 앵커로 옮김
    - 두 앵커 사이의 한 지점은 임계점으로 선언 → 임계점 도달 전 스와이프 모션 끝나면 시작 앵커 반환

- swipeable() 모디파이어 적용 시 주요 파라미터
    - state : 재구성을 하는동안 상태 저장
    - anchers : 맵 선언으로 앵커 지점들과 상태들 연결
    - orientation : 스와이프 제스처 방향
    - enabled : 스와이프 감지 활성화 여부
    - reverseDirection : 스와이프 반대 방향 효과
    - thresholds : 임계점 위치 저장 (람다로 선언 - 앵커 사이의 거리를 백분율 또는 고정 위치로 설정 가능)
    - resistance : 스와이프 모션이 앵커를 넘었을 때 적용할 저항
    - velocityThreshold : 스와이프 속도의 최소값

- 구현

    ```kotlin
    @Composable
    fun MainScreen() {
        val parentBoxWidth = 320.dp
        val childBoxSides = 30.dp
    
        val swipeableState = rememberSwipeableState("L")
        val widthPx = with(LocalDensity.current) { (parentBoxWidth - childBoxSides).toPx() }
    
        val anchors = mapOf(0f to "L", widthPx / 2 to "C", widthPx to "R")
    
        Box {
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .width(parentBoxWidth)
                    .height(childBoxSides)
                    .swipeable(
                        state = swipeableState,
                        anchors = anchors,
                        thresholds = { _, _ -> FractionalThreshold(0.5f) },
                        orientation = Orientation.Horizontal
                    )
            ) {
                Box(
                    Modifier.fillMaxWidth().height(5.dp).background(Color.DarkGray)
                        .align(Alignment.CenterStart)
                )
                Box(
                    Modifier.size(10.dp).background(
                        Color.DarkGray,
                        shape = CircleShape
                    ).align(Alignment.CenterStart)
                )
                Box(
                    Modifier.size(10.dp).background(
                        Color.DarkGray,
                        shape = CircleShape
                    ).align(Alignment.Center)
                )
                Box(
                    Modifier.size(10.dp).background(
                        Color.DarkGray,
                        shape = CircleShape
                    ).align(Alignment.CenterEnd)
                )
    
                Box(
                    Modifier
                        .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                        .size(childBoxSides)
                        .background(Color.Blue),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        swipeableState.currentValue,
                        color = Color.White,
                        fontSize = 22.sp
                    )
                }
            }
        }
    }
    ```