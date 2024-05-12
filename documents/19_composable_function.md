## 1. 컴포저블 함수

- 컴포즈로 사용자 인터페이스를 만들기 위해 사용하는 코틀린 함수
- `@Composable` 어노테이션을 이용해 선언
- 호출 시 → 앱 안에서 사용자 인터페이스의 해당 영역이 렌더링될 때 사용자에게 표시되고, 동작하는 방식을 정의하는 데이터와 프로퍼티 집합을 전달
- 컴포즈 런타임으로 통해 렌더링됨

### Compose 함수의 특징

- UI 일부를 설명합니다.
- 아무것도 반환하지 않습니다.
- 몇 개의 입력을 받아서 화면에 표시되는 내용을 생성합니다.
- 여러 UI 요소를 내보낼 수도 있습니다.

### Compose 함수의 이름

- 첫 글자는 **대문자로 표기**한다.
- 함수 이름은 **명사**여야 한다.
    - 동사 / 동사구 ❌ : `DrawTextField()`
    - 명사화된 전치사 ❌ : `TextFieldWithLink`
    - 형용사 ❌ : `Bright()`
    - 부사 ❌ : `Outside()`
    - 형용사+명사는 ⭕ : `RoundIcon()`

### Preview

- `@Preview`로 미리보기가 가능하다.
    
    ```kotlin
    @Preview
    @Composable
    fun DefaultPreview() {
       GreetingCardTheme {
           Greeting("Meghan")
       }
    }
    ```
    
- Preview에 매개변수를 전달할 수 있다.
    
    ```kotlin
    @Preview(showBackground = true)	// 배경화면 미리보기
    
    @Preview(
    	name = "My Preview",    // Preview에 제목 지정
        showSystemUi = true     // 시스템UI (휴대전화 화면)을 포함한 미리보기
    )
    ```
    

## 2. 컴포저블 함수 분류

- Stateful(상태) ↔ Stateless(비상태) 분류
- 상태 : 앱 실행 중 변경할 수 있는 모든 값 - 텍스트 필드에 입력된 문자열, 체크박스 상태 등
- 하나의 Composable 함수는 해당 함수 혹은 다른 Composable 함수가 호출한 Composable의 형태나 동작을 정의하는 상탯값을 저장할 수 있음
- 상태값을 저장하기 위해서 `remember` 키워드를 이용하고 `mutableStateOf` 함수를 호출
    
    Stateful :
    
    ```kotlin
    @Composable
    fun DemoScreen() {
    		var slidePosition by remember { mutableStateOf(20f) }
    }
    ```
    
    Stateless :
    
    ```kotlin
    @Composable
    fun DemoSlider(sliderPosition : Float) {
    		Slider(
    				modifier = Modifier.padding(10.dp),
    				value = sliderPosition
    		)
    }
    ```
    
    - 전달된 상태값을 이용하지만, 스스로 상태값을 저장하지는 않으므로 Stateless
    

## 3. 컴포저블 함수 구문

- 함수 안에서 다른 컴포저블 호출 가능
    
    ```kotlin
    @Composable
    fun MyFunction() {
    		Text("Hello")
    }
    ```
    
- 파라미터 값 받기 가능
    
    ```kotlin
    @Composable
    fun CustomText(text: String, color: Color) {
        Text(
            text = text,
            color = color
        )
    }
    
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        CustomText(
    		    text = "Hello", 
    		    color = Color.Blue
        )
    }
    
    ```
    
- 모든 코틀린 로직 코드를 포함할 수 있음
    - Switch 컴포저블
        
        ```kotlin
        @Composable
        fun CustomSwitch() {
            val checked = remember { mutableStateOf(true) }
            
            Column {
        		    Switch(
        				    checked = checked.value,
        				    onCheckedChange = {checked.value = it }
        				)
        				if (checked.value) {
        						Text("On")
        				} else {
        						Text("Off")
        				}
            }
        }
        ```
        
    - List 컴포저블
        
        ```kotlin
        @Composable
        fun CustomList(items: List<String>) {
            Column {
        		    for (item in items) {
        				    Text(item)
        				    Divider(color = Color.Black)
        		    }
            }
        }
        
        @Preview(showBackground = true)
        @Composable
        fun DefaultPreview() {
        		MyAppTheme {
        				CustomList(listOf("One", "Two", "Three"))
        		}
        }
        ```
        

## 4. 파운데이션 & 머터리얼

- 일반적으로 앱 개발 시, 커스텀 컴포저블 (앞 예시의 CustomText)와 내장 컴포넌트 (앞 예시의 Text) 조합
- 컴포즈에서 번들로 제공하는 컴포저블 : 3분류
    1. 레이아웃
        
        컴포넌트를 화면에 배치하고, 컴포넌트들이 상호 동작하는 방법 정의
        
        ![2024-05-13_02-09-42.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/732e572f-bc80-4515-be7c-6c518ea8578b/2024-05-13_02-09-42.jpg)
        
    2. 파운데이션
        
        기능을 제공하는 최소한의 컴포넌트 집합 - 앱의 형태나 행동 자유롭게 정의
        
        ![2024-05-13_02-10-36.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/886bde4a-2546-4615-b09e-cdd3c7723832/2024-05-13_02-10-36.jpg)
        
    3. 머터리얼
        
        구글의 머터리얼 디자인 만족하도록 특별히 디자인되어 제공
        
        ![2024-05-13_02-11-15.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/2cc7a1d7-f1b9-4155-9ac9-3569cbd7a67e/2024-05-13_02-11-15.jpg)
