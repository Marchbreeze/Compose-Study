## 1. Slot API 선언하기

- Slot API
    - 컴포저블에 대한 UI를 추가해 호출자가 슬롯 안에 표시할 컴포저블을 지정할 수 있도록 허가하는 것을 의미
    - 하나의 Slot API = 하나 이상의 요소가 비어있는 UI 템플릿
    - 컴포저블이 호출될 때 파라미터로 전달됨

- Slot API 선언하기
    
    ```kotlin
    @Composable
    fun SlotDemo(middleContent: @Composable () -> Unit) {
    		Column {
    				Text("Top Text")
    				middleContent()
    				Text("Bottom Text")
    		}
    }
    ```
    

## 2. Slot API 컴포저블 호출하기

- 컴포저블을 middleContent()로 표시하기
    
    ```kotlin
    @Composable
    fun ButtonDemo() {
    		Button(onClick = {}) {
    				Text("Click me")
    		}
    }
    ```
    

- SlotDemo 함수 호출하기
    
    ```kotlin
    SlotDemo(middleContent = { ButtonDemo() })
    ```
    
    ```kotlin
    SlotDemov{
    		ButtonDemo()
    }
    ```
    
- 슬롯마다 별도의 Slot API를 이용할 필요는 X
    
    ```kotlin
    @Composable
    fun SlotDemo(
    		topContent: @Composable () -> Unit,
    		middleContent: @Composable () -> Unit,
    		bottomContent: @Composable () -> Unit) {
    				Column {
    						topContent()
    						middleContent()
    						bottomContent()
    				}
    }
    ```
    
    ```kotlin
    SlotDemo(
    		{ Text("Top Text") }.
    		{ ButtonDemo() },
    		{ Text("Bottom Text") }
    )
    ```
