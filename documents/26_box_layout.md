## 1. BoxLayout

- Row, Column이 수평의 행 또는 수직의 열로 자식들을 구조화하는 것과 달리, BoxLayout은 자식들을 위로 쌓아 올림(스택)
- 첫번째로 호출된 자식이 스택의 가장 아래에 위치하며, 호출된 순서에 따라 쌓이게 됨

- 겹치는 예시
    
    ```kotlin
    fun MainScreen6() {
        Box {
            val height = 200.dp
            val width = 200.dp
            TextFontCell(text = "1", Modifier.size(width, height))
            TextFontCell(text = "2", Modifier.size(width, height))
            TextFontCell(text = "3", Modifier.size(width, height))
        }
    }
    ```
    
    ![2024-05-20_23-18-53.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/9cab8056-d08d-41b3-9eaa-d0a9c02fbf72/2024-05-20_23-18-53.jpg)
    

## 2. Box 정렬

- 콘텐츠의 위치 설정
    
    ![2024-05-20_23-19-28.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/f58c19e1-e095-4f7a-b3f5-dd688d3dda26/2024-05-20_23-19-28.jpg)
    

- 예시
    
    ```kotlin
    @Composable
    fun MainScreen6() {
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier.size(400.dp, 400.dp)
        ) {
            val height = 200.dp
            val width = 200.dp
            TextFontCell(text = "1", Modifier.size(width, height))
            TextFontCell(text = "2", Modifier.size(width, height))
            TextFontCell(text = "3", Modifier.size(width, height))
        }
    }
    ```
    
    ![2024-05-20_23-20-48.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/f0dba79b-7251-4bcb-a40e-0d1bede46cd6/2024-05-20_23-20-48.jpg)
    

## 3. BoxScope 모디파이어

- `align()` : 지정한 Alignment의 값으로 Box 콘텐츠 영역 안의 자식 정렬
- `matchParentSize()` : 모디파이어가 적용된 자식의 크기를 부모 Box 크기에 맞춤
    
    ```kotlin
    fun MainScreen6() {
        Box (modifier = Modifier.size(90.dp, 90.dp)){
            Text(text = "1", Modifier.align(Alignment.TopStart))
            Text(text = "2", Modifier.align(Alignment.Center))
            Text(text = "3", Modifier.align(Alignment.BottomEnd))
        }
    }
    ```
    
    ![2024-05-20_23-32-02.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/bcab8fd8-7818-4453-a711-633e42424aca/2024-05-20_23-32-02.jpg)
    

## 4. clip() 모디파이어

- clip() 모디파이어 활용 → 컴포저블을 특정한 형태로 렌더링되도록 설정 가능
- Box 에만 지정할 수 있는 것은 아니지만, Box 컴포넌트가 형태를 자르는 것을 보여주기에 가장 좋은 예시
- Shape 값으로는 RectangleShape, CircleShape, RoundedCornerShape, CutCornerShape가 있음
    
    ```kotlin
    @Composable
    fun MainScreen6() {
        Box(
            modifier = Modifier
                .size(90.dp, 90.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray)
        ) {
            Text(text = "1", Modifier.align(Alignment.TopStart))
            Text(text = "2", Modifier.align(Alignment.Center))
            Text(text = "3", Modifier.align(Alignment.BottomEnd))
        }
    }
    ```
    
    ![2024-05-20_23-57-38.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/4fa92a8c-07e5-423d-ba8f-22dcfccb2e24/2024-05-20_23-57-38.jpg)
