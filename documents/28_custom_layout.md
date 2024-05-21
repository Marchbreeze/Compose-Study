## 2. 커스텀 레이아웃

### (1) 커스텀 레이아웃

- 컴포즈가 제공하는 커스텀 레이아웃을 이용하면, 직접 레이아웃 컴포넌트를 디자인하고 자식 요소의 크기와 위치를 자유롭게 제어할 수 있음

- 커스텀 레이아웃 구문
    
    ```kotlin
    @Composable
    fun DoNothingLayout(
    		modifier : Modifier = Modifier,
    		content : @Composable () -> Unit
    		) {
    		Layout(
    				modifier = modifier,
    				content = content
    		) { measurables, constraints ->
    				val placeables = measurables.map { measurable ->
    						// 각 자식들을 측정
    						measurable.measure(constraints)
    				}
    				layout(constraints.maxWidth, constraints.maxHeight) {
    						placeables.forEach { placeable ->
    								placeable.placeRelative(x = 0, y = 0)
    						}
    				}
    		}
    }
    ```
    
    - 모디파이어 하나와 Slot API를 통해 표시되는 콘텐츠를 받음
    - 이후 Layout() 컴포저블을 호출하고, 해당 컴포저블 뒤에 람다를 받음
    - measurable 파라미터는 콘텐츠 안에 포함된 모든 자식 요소를 포함
    - constraints 파라미터는 자식 요소에 지정될 수 있는 최대/최소 폭 ㅗ또는 높이 값 포함

- 커스텀 레이아웃 이용
    
    ```kotlin
    DoNothingLayout(Modifier.padding(8.dp) {
     Text("1")
     Text("2")
     Text("3")
    }
    ```
    
    - 커스텀 레이아웃이 자식 요소를 재배치하지 않았으므로, 스택으로 3개의 텍스트가 쌓이는 결과가 보여짐
    

### (2) 프로젝트 실습

- CascadeLayout 설정
    
    ```kotlin
    @Composable
    fun CascadeLayout(
        spacing: Int = 0,
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    ) {
        Layout(
            modifier = modifier,
            content = content
        ) { measurables, constraints ->
            var indent = 0
            layout(constraints.maxWidth, constraints.maxHeight) {
                val placeables = measurables.map { measurable ->
                    measurable.measure(constraints)
                }
                var yCoord = 0
                placeables.forEach { placeable ->
                    placeable.placeRelative(x = indent, y = yCoord)
                    indent += placeable.width + spacing
                    yCoord += placeable.height + spacing
                }
            }
        }
    }
    
    @Composable
    fun MainScreen8() {
        Box {
            CascadeLayout(spacing = 20) {
                Box(modifier = Modifier.size(60.dp).background(Color.Blue))
                Box(modifier = Modifier.size(80.dp, 40.dp).background(Color.Green))
                Box(modifier = Modifier.size(70.dp, 100.dp).background(Color.Gray))
                Box(modifier = Modifier.size(50.dp).background(Color.Cyan))
                Box(modifier = Modifier.size(20.dp).background(Color.Magenta))
            }
        }
    }
    ```
    
    ![2024-05-21_15-40-34.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/e462c789-4776-4c82-a134-f77155d19371/2024-05-21_15-40-34.jpg)
