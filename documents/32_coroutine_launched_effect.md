## 1. 코루틴

- 코루틴 : 자신이 실행된 스레드를 정지시키지 않으면서, 비동기적으로 실행되는 코드 블록
    - 다중 스레딩 옵션을 이용하는 것보다 효율적, 적은 리소스

- 기존 스레드
    - 특정 시점에 병렬적으로 실제 실행될 수 있는 스레드 개수는 제한됨
    - CPU 코어 숫자보다 많은 숫자가 필요 시, 시스템은 스레드 스케줄링을 수행해 스래드의 실행을 공유할 수 있는 정책 설정
    - 스레드의 생성, 스케줄링, 파기를 위해 많은 작업 실행됨
- 코루틴
    - 코루틴이 실행될 때마다 새로운 스레드를 실행하고, 코루틴이 종료될 때 해당 스레드를 파기하지 않음
    - 대신 활성화 상태의 스레드 풀을 유지하고, 코루틴들을 해당 스레드에 할당하는 방법을 관리
    - 활성화된 코루틴이 중지되면 해당 코루틴은 런타임에 의해 저장되고, 다른 코루틴이 재실행됨
    - 코루틴이 재시작되면 스레드 풀의 비어있는 스레드에 원복

### (1) 코루틴 스코프

- 모든 코루틴은 명시적인 스코프 안에서 실행 → 개별 코루틴이 아닌 그룹으로 관리됨 (코틀린의 누수를 막는 데 중요)
    - GlobalScope : 최상위 코루틴 실행 (비권장)
    - viewModelScope : 뷰모델 인스턴스 안에서의 사용 (뷰모델 파기 시 취소)
    - lifecycleScope : 해당 라이프사이클 소유자 파기 시 취소

- 대부분의 컴포저블에서, 코루틴 스코프에 접근하는 방법 :
    
    ```kotlin
    val coroutineScope = rememberCoroutineScope()
    ```
    

- 일시 중단 함수
    - 해당 함수가 이후 일시정지 및 재시작될 수 있는 함수로 실행
    - 메인 함수를 막지 않는 함수
    
    ```kotlin
    suspend fun performSlowTask() {}
    ```
    

### (2) 코루틴 디스패처

- 코틀린은 다양한 유형의 비동기 처리를 위한 스레드 유지
- 디스패처는 코루틴들을 적절한 스레드에 할당하고, 라이프사이클동안 해당 코루틴들을 중지하고 재시작하는 책임을 짐
    1. Dispatchers.Main : 메인 스레드 (UI 변경, 경량의 테스크 실행)
    2. Dispatchers.IO : 네트워크, 디스크, 데이터베이스 작업 수행
    3. Dispatchers.Default : 많은 CPU를 수행하는 테스크
    
    ```kotlin
    coroutineScope.launch(Dispatcher.IO) {
    		performSlowTask()
    }
    ```
    

### (3) 코루틴 빌더

- launch
    - 현재 스레드를 중단하지 않고 코루틴을 시작 & 결과 반환 X
    - 전통적인 함수 안에서 중지된 함수를 호출할 때, 결과를 처리할 필요가 없을 때 사용 (finre and forget)
- async
    - 현재 스레드를 중단하지 않고 하나의 코루틴을 시작, 호출자가 await() 함수를 이용해 결과를 기다림
    - 여러 코루틴을 동시에 사용해야 할 때 사용
- withContext
    - 부모 코루틴에서 사용된 것과 다른 컨텍스트에서 코루틴 실행 가능
- coroutineScope
    - 중지된 함수가 여러 코루틴을 동시에 실행하면서, 동시에 모든 코루틴이 완료되었을 때만 특정 액션 발생
    - 호출 함수는 모든 자식 코루틴이 완료된 뒤 결과 반환
    - 하위 코루틴들 중 어느 하나에서라도 실패가 발생하면 모든 코루틴 취소
- supervisorScope
    - coroutineScope에서 하위 코루틴들 중 실패가 발생해도 진행
- runBlocking
    - 한 코루틴을 실행하고 해당 코루틴이 완료될 떄까지 현재 스레드 중지

- 버튼 실행 예시
    
    ```kotlin
    val coroutineScope = rememberCoroutineScope()
    
    Button(onClick = {
    		coroutineScope.launch {
    				performSlowTask()
    		}
    }) {
    		Text(text = "CLick me")
    }		
    ```
    

### (4) 잡

- 모든 코루틴 빌더들의 호출은 Job 인스턴스를 반환, 해당 코루틴의 라이프사이클을 추적하고 관리할 수 있음
- 부모 잡을 취소하면 자식 잡도 취소됨
    - Launch의 경우, 자식의 예외가 부모의 잡을 취소할 수 있음

### (5) 코루틴 채널

- 채널 : 데이터 스트림을 포함하는 코루틴 사이의 커뮤니케이션을 간단하게 구현 가능
- Channel 인스턴스를 생성한 뒤, send() 로 데이터 전달 & receive()로 다른 코루틴 전달
    
    ```kotlin
    val channel = Channel<Int>()
    
    coroutineScope.launch) {
    		coroutineScope.launch(Dispatchers.Main) { performTask1() }
    		coroutineScope.launch(Dispatchers.Main) { performTask2() }
    }
    
    suspend fun performTask1() {
    		(1..6).forEach {
    				channel.send(it)
    		}
    
    suspend fun performTask2() {
    		repeat(6) {
    				println("Received: ${channel.receive()}")
    		}
    ```
    

## 2. LaunchedEffect

- 코루틴을 부모 컴포저블의 범위 안에서 실행하는 것을 안전하지 않음
    
    ```kotlin
    // 에러 발생 코드
    @Composable
    fun Greeting(name: String) {
    		val coroutineScope = rememberCoroutineScope()
    
    		coroutineScope.launch(Dispatcher.IO) {
    				performSlowTask()
    		}
    }
    ```
    
    - 비동기적인 코드가 해당 컴포저블의 라이프사이클을 고려하지 않고 다른 스코프로부터 컴포저블의 상태를 변경하고자 할 때, 부작용 발생
    - 해당 컴포저블이 존재하는 동안 코루틴이 차례로 계속 실행될 가능성 존재

- LaunchedEffect 또는 SideEffect 컴포저블 바디 안에서 코루틴을 실행해야 함
    - 두 컴포저블은 부모 컴포저블의 라이프사이클을 인식하기 때문에 코루틴 안전하게 실행 가능
    
    ```kotlin
    @Composable
    fun Greeting(name: String) {
    		val coroutineScope = rememberCoroutineScope()
    
    		LaunchedEffect(key1 = Unit) {
    				coroutineScope.launch() {
    						performSlowTask()
    				}
    		}
    }
    ```
    
    - key 파라미터값 : 재구성을 통해 코루틴 동장 통제
    - key 파라미터가 변경되지 않으면, LaunchedEffect는 해당 부모 컴포저블의 여러 재구성 과정에서도 동일한 코루틴 유지
    - key 파라미터가 변경되면, 현재 코루틴을 취소하고 새로운 코루틴 실행
    - Unit 전달 시, 재구성 과정에서 해당 코루틴을 재생성하지 않음을 의미
    - SideEffect
        - 부모의 재구성이 완료된 뒤 실행
        - key 파라미터를 받지 않고 부모 컴포저블이 재구성될 때마다 수행
