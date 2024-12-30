## 1. 플로

- Flow : 코루틴 기반의 비동기 태스크들로부터 순차적으로 여러 값 반환하도록 설계됨
- 생산자 - 중재자 - 소비자
    - 생산자 : 가상의 네트워크 커넥션으로부터 데이터 스트림을 꺼냄 → 사용할 수 있을 때 방출(emit)
    - 중재자 : 데이터가 소비자에 도착하기 전에 스트림 필터링, 변환
    - 소비자 : 생산자가 방출한 데이터를 수집(collect)

1. 생산 및 방출
    - flowOf(), asFlow() 빌더는 소비자가 수집을 시작하는 즉시 자동으로 데이터 방출

        ```kotlin
        val ofFlow = flowOf(2,3,4,5)
        val arrayFlow = arrayOf<String>("2", "3", "4").asFlow()
        ```

    - flow 빌더는 각 값이 사용 가능해졌을 때 방출하는 코드를 직섭 작성해줘야 함

        ```kotlin
        val myFlow: Flow<Int> = flow {
        		for (i in 0..9) {
        				emit(i)
        				delay(2000)
        		}
        }
        ```


1. 필요 시 중재자 사용

    ```kotlin
    val newFlow = myFlow.map {
    		"Current value = $it"
    }
    ```

2. 수집
    - 해당 플로 인스턴스에 대해 collectAsState()로 상태로 변환

        ```kotlin
        @Composable
        fun ScreenSetup(viewModel: DemoViewModel = viewModel()) {
        		MainScreen(viewModel.myFlow)
        }
        
        @Composable
        fun MainScreen(flow: Flow<Int>) {
        		val count by flow.collectAsState(initial = 0)
        }
        ```

    - collectAsState()를 사용할 수 없는 결과를 얻어야 하는 경우, collect 직접 호출

        ```kotlin
        @Composable
        fun MainScreen(flow: Flow<Int>) {
        		val count by remember { mutableStateOf<String>("Current value =")}
        
        		LaunchedEffect(Unit) {
        				try {
        						flow.collect {
        								count = it
        						}
        				} finally {
        						count = "Flow ended"
        				}
        		}
        }
        ```

        - collect : 처리 중에도 모든 값 수집
        - collectLatest : 처리 완료 전 신규 값 도착 시 신규 값으로 처리 재시작
        - conflate : 처리 완료 전 신규 값 도착 시 무시
    - buffer()를 활용해서 방출되는 모든 값을 수집하면서 처리할 수 있음

        ```kotlin
        @Composable
        fun MainScreen(flow: Flow<Int>) {
        		val count by remember { mutableStateOf<String>("Current value =")}
        
        		LaunchedEffect(Unit) {
        				try {
        						flow
        							  .buffer()
        						    .collect {
        								    count = it
        								}
        				} finally {
        						count = "Flow ended"
        				}
        		}
        }
        ```


## 2. 핫/콜드 플로

- 콜드 플로 : 플로 타입을 이용해 선언된 스트림 - 소비자가 값의 수집을 시작해야지만 실행
- 핫 플로 : 소비자가 값을 수집하는지에 관계없이 생산자는 즉시 값을 방출 (StateFlow & SharedFlow)

### (1) 스테이트플로

- 상태 변화를 관찰하는 데 사용됨
- 각 StateFlow 인스턴스는 시간에 따라 변경되는 단일 값을 저장하고 변경 발생을 모든 소비자에게 알림

    ```kotlin
    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()
    
    fun increaseValue() {
    		_stateFlow.value += 1
    }
    ```

    ```kotlin
    val count by viewModel.stateFlow.collectAsState()
    ```


### (2) 셰어드플로

- 스테이트플로가 제공하는 것보다 일반적인 목적의 스트리밍 옵션 제공
    - 초기값 제공 X
    - 수집 이전의 방출된 값을 컬렉터에 다시 재생되도록 할 수 있음
    - value 프로퍼티를 사용하는 대신 방출함

    ```kotlin
    private val _sharedFlow = MutableSharedFlow<Int>(
    		replay = 10,
    		onBufferOverFlow = BufferOverFlow.DROP_OLDEST
    )
    val sharedFlow = _sharedFlow.asSharedFlow()
    
    fun startSharedFlow() {
    		viewModelScope.launch {
    				for (i in 1..5) {
    						_sharedFlow.emit(i)
    						delay(2000)
    				}
    		}
    }
    ```

    ```kotlin
    val count by viewModel.sharedFlow.collectAsState(initial = 0)
    ```