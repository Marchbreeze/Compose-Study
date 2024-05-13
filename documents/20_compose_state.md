## 1. 상태

- 상태 : 시간에 따라 변경될 수 있는 값
    - 상태 변수에 할당된 값은 기억되어야 함 (지난번 호출했을 때의 상탯값을 기억해야)
    - 상태 변수의 변경은 UI를 구성하는 컴포저블 함수 계층 트리의 전체에 영향을 미침

## 2. 재구성

- 재구성 (Recomposition)
    - 한 컴포저블 함수에서 다른 함수로 전달된 데이터는 대부분 부모 함수에서 상태로서 선언됨
    - 이는 부모 컴포저블의 상태 변화가 모든 자식 컴포저블에 반영되며, 상태가 전달됨을 의미함

- 재구성은 컴포저블 함수의 계층 안에서 상태값이 변경될 때 발생
    - 상태 변화 감지 시, 액티비티의 모든 컴포저블 함수에 대해 해당 상태 변화에 영향을 받는 모든 함수를 재구성
    - 재구성 = 해당 함수들을 다시 호출하고, 새로운 상태값을 전달하는 것

- 지능적 재구성 (Intelligent Recomposition)
    - 상태값 변경마다 모든 컴포저블 트리를 재구성하면 매우 비효율적 → 컴포즈 : 변화에 직접 영향을 받는 함수만 재구성해 오버헤드를 피함
    

## 3. 컴포저블에서 상태 선언하기

- 상태값을 선언할 때는 MutableState 객체로 해당 값을 감싸야 함
- MutableState : 옵저버블 타입 → 상태 변수를 읽는 모든 함수는 이 옵저버블 상태를 구독
- 상태값 변경 시 모든 구독 함수에 재구성이 트리거

1. 실습
    
    ```kotlin
    // MyTextField를 하나의 상태 변수, 그리고 입력에 따라 상태를 변경하는 하나의 이벤트 핸들러를 포함한 상태 컴포저블 함수로 만듬
    @Composable
    fun DemoScreen() {
        MyTextField()
    }
    
    @Composable
    fun MyTextField() {
        // mutableStateOf 런타임 함수 호출 후 초기 상태값 전달 (현재 상태값 유지 위해 remember 키워드 활용)
        var textState = remember { mutableStateOf("Hello") }
        // MutableState 인스턴스의 value 프로퍼티를 읽고 설정해 상태를 업데이트
        val onTextChange = { text: String ->
            textState.value = text
        }
        // 현재 상태값을 TextField에 할당
        TextField(value = textState.value, onValueChange = onTextChange)
    
    }
    
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        ComposeStudyTheme {
            DemoScreen()
        }
    }
    ```
    
2. Delegate 활용
    - 상태 선언의 좀 더 일반적이고 간결한 접근 방식
    - by 키워드를 통해서 프로퍼티를 위임(delegation)
    - 이벤트 핸들러에서 MutableState 값 프로퍼티를 직접 참조하지 않고도 접근 가능 (.value X)
    
    ```kotlin
    @Composable
    fun MyTextField() {
        // by 키워드
        var textState by remember { mutableStateOf("Hello") }
        // 프로퍼티 직접 참조하지 않고도 접근 가능
        val onTextChange = { text: String ->
            textState = text
        }
        TextField(value = textState, onValueChange = onTextChange)
    
    }
    ```
    
3. 세터, 게터 활용
    
    ```kotlin
    @Composable
    fun MyTextField() {
        var (textValue, setText) = remember { mutableStateOf("Hello") }
        val onTextChange = { text: String ->
            setText(text)
        }
        TextField(value = textValue, onValueChange = onTextChange)
    
    }
    ```
    

## 4. 단방향 데이터 흐름

- 단방향 데이터 흐름(unidirectional data flow)
    
    > 한 컴포저블에 저장된 상태는 자식 컴포저블 함수들에 의해 직접 변경되어서는 안된다.
    > 

- FuntionA가 Boolean 형태의 상태값을 포함하고, Switch를 포함한 FunctionB를 호출하는 경우
    - 스위치의 목적은 상태값을 업데이트 해야함
    - but, 단방향 데이터 흐름을 따르면, FunctionB는 A의 상태값을 변경할 수 없음
    - 대신 !
        - FuntionA는 이벤트 핸들러를 선언하고(람다 형태), 자식 컴포저블에 상태값과 함께 파라미터로 전달함
        - FunctionB의 Switch는 스위치가 변경될 때마다 이벤트 핸들러를 호출해서 상태 업데이트
        
        ```kotlin
        @Composable
        fun FunctionA() {
            var switchState by remember { mutableStateOf(true) }
            var onSwitchChange = { value: Boolean ->
                switchState = value
            }
            FunctionB(switchState = switchState, onSwitchChange = onSwitchChange)
        }
        
        @Composable
        fun FunctionB(switchState: Boolean, onSwitchChange: (Boolean) -> Unit) {
            Switch(checked = switchState, onCheckedChange = onSwitchChange)
        }
        ```
        
        ![2024-05-14_01-18-19.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/ccceddf8-bb91-4206-8db1-2835cad0785e/2024-05-14_01-18-19.jpg)
        

## 5. 상태 호이스팅

- 상태 호이스팅 (State Hoisting)
    - 상태를 자식 컴포저블에서 이를 호출한 부모 컴포저블로 들어올리는 의미
    - 자식 컴포저블에서 상태 업데이트가 필요한 이벤트 발생 시, 새로운 값을 전달하는 이벤트 핸들러 호출
    - 결과적으로 자식 컴포저블을 비상태 컴포저블로 만들기 떄문에 재사용성이 향상됨
        
        ![2024-05-14_01-28-22.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/5a34cde6-e93d-4b80-9d83-73329b392bf7/2024-05-14_01-28-22.jpg)
        
    
- 기존 코드 :
    
    ```kotlin
    @Composable
    fun DemoScreen() {
        MyTextField()
    }
    
    @Composable
    fun MyTextField() {
        var textState by remember { mutableStateOf("Hello") }
        val onTextChange = { text: String ->
            textState = text
        }
        TextField(value = textState, onValueChange = onTextChange)
    }
    ```
    
    - MyTextField 컴포저블은 자급자족적인 특성을 가짐 → 특별히 유용하지 않음
    - 사용자가 입력한 텍스트가 함수 호출에 접근할 수 없음 → 형제 함수들에게도 전달할 수 없음
    - 다른 상태나 이벤트 핸들러도 함수로 전달할 수 없음 → 재사용성의 저하

- 부모 컴포저블(DemoScreen)로 상태 들어올리기
    
    ```kotlin
    @Composable
    fun DemoScreen() {
        var textState by remember { mutableStateOf("Hello") }
        val onTextChange = { text: String ->
            textState = text
        }
        MyTextField(text = textState, onTextChange = onTextChange)
    }
    
    @Composable
    fun MyTextField(text: String, onTextChange: (String) -> Unit) {
        TextField(value = text, onValueChange = onTextChange)
    }
    ```
    
    - MyTextField = 재사용 가능한 비상태 컴포저블
    - 함수에 상태 추가하는 경우, 상태를 들어 올려 재사용성과 유연함을 높일 수 있는지 고려해야 함
    

## 6. 환경 설정 변경을 통한 상태 저장

- remember 기법은 환경 설정 변경(configuration change) 사이의 상태를 유지하지 않음
- ex. 기기 방향 변경, 시스템 폰트 변경
- 이러한 변경 발생 시 액티비티 전체를 다시 생성해야 함 → 새롭게 초기화된 액티비티는 이전 상태값을 하나도 기억하지 못함

- `rememberSaveable` 키워드
    - 환경 설정이 변경되어도 상태를 유지함
