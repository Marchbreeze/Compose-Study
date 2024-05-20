### (1) 표준 구문

```kotlin
fun Modifier.<커스텀 레이아웃 이름> (
		// 선택적 마라미터
) = layout { measurable, constraints ->
		// 요소의 위치와 크기를 조정한 코드
}
```

- measurable : 해당 모디파이어가 호출된 자식 요소가 배치될 정보
- constraints : 자식이 이용할 수 있는 최대/최소 폭과 높이 포함

### (2) 기본 위치

- 레이아웃 모디파이어는 부모 컨텍스트 안에서의 자식의 기본 위치에 대해서 신경 X
- “기본 위치를 기준으로” 자식의 위치를 계산 → 모디파이어는 0,0을 기준으로 새로운 위치를 계산한 뒤 새로운 오프셋으로 반환 → 실제 좌표에 적용해 자식의 위치 변경

```kotlin
fun Modifier.exampleLayout(                                                                                                     
    x: Int,                                                                                                                     
    y: Int                                                                                                                      
) = layout { measurable, constraints ->                                                                                         
    val placeable = measurable.measure(constraints)                                                                             
                                                                                                                                
    layout(placeable.width, placeable.height) {                                                                                  
        placeable.placeRelative(x, y)                                                                                           
    }                                                                                                                            
}                                                                                                                                                                 
```

- measurable.measure(constraints)
    - 자식의 측정값 → Placeable 인스턴스가 반환되며, 높이와 폭 값을 가짐
- Placeable 인스턴스의 메서드를 호출해 부모 컨텐츠 영역 안에 있는 요소들의 새로운 위치를 지정해줌

- 실습
    
    ```kotlin
    @Composable                                                                                    
    fun MainScreen7() {                                                                            
        Box(modifier = Modifier.size(120.dp, 80.dp)) {                                              
            ColorBox(modifier = Modifier                                                           
                .exampleLayout(90, 50)                                                             
                .background(Color.Blue))                                                           
        }                                                                                          
    }                                                                                              
    ```
    
    ![2024-05-21_00-48-38.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/6f38b7c7-bdd0-4e61-9c75-e195bb8634da/2024-05-21_00-48-38.jpg)
    
    ![2024-05-21_00-48-24.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/ca04336f-157e-4776-8ad8-4770158aff5c/2024-05-21_00-48-24.jpg)
    

### (3) 정렬 선 다루기

- ColorBox의 왼쪽 위 모서리를 특정한 x, y 좌표로 이동시킴
- = 박스를 사각형의 왼쪽과 위쪽에 해당하는 두 정렬 선 (alighment line)의 교점에 놓음
    
    ![2024-05-21_00-55-24.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/828170e0-10f7-4a83-9d15-33f84a4906fd/2024-05-21_00-55-24.jpg)
    

- 실제로 가상 정렬 선에 따라 위치를 설정할 수 있음
    
    ```kotlin
    fun Modifier.exampleLayout(
        fraction: Float
    ) = layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
    
        val x = -(placeable.width * fraction).roundToInt()
    
        layout(placeable.width, placeable.height) {
            placeable.placeRelative(x = x, y = 0)
        }
    }
    ```
    
    - x, y 좌표 전달 대신, 새로운 위치는 부모가 정의한 기본 좌표에 따라 상대적으로 계산됨
    - x : placeable 객체로부터 자식의 폭을 받아서 fractino 파라미터 값을 곱함
    - 정렬 선을 오른쪽으로 옮기는 것은 자식을 왼쪽으로 옮기는 것과 같기 때문에 음수를 곱합
    
    ```kotlin
    @Composable
    fun MainScreen7() {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(120.dp, 80.dp)) {
            Column {
                ColorBox(
                    modifier = Modifier
                        .exampleLayout(0f)
                        .background(Color.Blue)
                )
                ColorBox(
                    modifier = Modifier
                        .exampleLayout(0.25f)
                        .background(Color.Gray)
                )
                ColorBox(
                    modifier = Modifier
                        .exampleLayout(0.5f)
                        .background(Color.Green)
                )
            }
        }
    }
    ```
    
    ![2024-05-21_00-55-15.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/5fe63375-f548-435b-baf6-971c492215ed/2024-05-21_00-55-15.jpg)
    

### (4) 베이스라인 다루기

- Text 컴포저블은 텍스트콘텐츠 베이스라인을 따라서 정렬 가능
- FirstBaseline, LastBaseline 정렬 선은 Text 컴포넌트 안에 포함된 컨텐츠의 첫번째 행과 마지막 행의 바닥선에 해당됨
    
    ```kotlin
    val firstBaseline = placeable[FirstBaseLine]
    val lastBaseline = placeable[LastBaseLine]
    ```
