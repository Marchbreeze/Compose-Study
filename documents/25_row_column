## 1. Row & Column 컴포넌트

- Text 배열해보기
    
    ```kotlin
    @Composable
    fun TextCell(text: String, modifier: Modifier = Modifier) {
        val cellModifier = Modifier
            .padding(4.dp)
            .size(100.dp, 100.dp)
            .border(width = 4.dp, color = Color.Black)
    
        Text(text = text,
            cellModifier.then(modifier),
            fontSize = 70.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center)
    }
    
    @Composable
    fun MainScreen3() {
        Column {
            Row {
                Column {
                    TextCell(text = "1")
                    TextCell(text = "2")
                    TextCell(text = "3")
                }
                Column {
                    TextCell(text = "4")
                    TextCell(text = "5")
                    TextCell(text = "6")
                }
                Column {
                    TextCell(text = "7")
                    TextCell(text = "8")
                }
            }
            Row {
                TextCell(text = "9")
                TextCell(text = "10")
                TextCell(text = "11")
            }
        }
    }
    ```
    
    ![2024-05-20_18-17-09.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/19634b78-5269-4cf7-b5a7-2defd9ba7927/2024-05-20_18-17-09.jpg)
    

## 2. 레이아웃 정렬

- Row, Column은 자식 요소, 다른 컴포저블, 크기 관련 모디파이어들에 따라 달라짐
- 기본적으로 가장 왼쪽 위 모서리를 기준으로 정렬됨
- 수직 방향 축의 기본 정렬 : verticalAlignment 로 변경
    
    ```kotlin
    @Composable
    fun MainScreen3() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.size(width = 400.dp, height = 200.dp)
        ) {
            TextCell(text = "1")
            TextCell(text = "2")
            TextCell(text = "3")
        }
    }
    ```
    
    ![2024-05-20_18-27-40.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/1ef5b613-5820-470a-aea0-65a6c31e676b/2024-05-20_18-27-40.jpg)
    
- Row의 수직 방향 정렬 파라미터
    
    ![2024-05-20_18-30-11.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/255b7e3b-8f07-4d23-86d1-f475b7354d12/2024-05-20_18-30-11.jpg)
    
- Column의 수평축 방향 정렬 파라미터
    
    ![2024-05-20_18-30-04.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/7a741f1e-2304-47bc-a949-f370aa95d0b8/2024-05-20_18-30-04.jpg)
    

## 3. 레이아웃 배열 위치 조정

- 정렬과 달리 배열(Arrangement)은 자식의 위치를 컨테이너와 동일 축을 따라 제어
- Row는 수평 방향
    
    ![2024-05-20_18-39-14.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/e4d8effa-1dbc-4b72-b46f-1678350e2c0c/2024-05-20_18-39-14.jpg)
    
    ![2024-05-20_18-39-21.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/a209c43b-5210-47ba-aabf-c27f3709c7db/2024-05-20_18-39-21.jpg)
    
- Column은 수직 방향
    
    ![2024-05-20_18-39-36.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/6ab08565-fc7d-442e-ad34-196c13d116d8/2024-05-20_18-39-36.jpg)
    
    ![2024-05-20_18-39-43.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/83725c60-8be8-4fae-89c2-ebf0125d1bda/2024-05-20_18-39-43.jpg)
    

## 4. 레이아웃 배열 간격 조정

- 배열 간격 조정을 이용해 Row 또는 Column 안의 자식 컴포넌트들의 콘텐츠 영역 안에서 간격을 조정함
- horizontalArrangement, verticalArrangement 파라미터로 정의함
    
    ![2024-05-20_18-43-00.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/f3745a61-c88d-4c5a-8964-9132c8b488eb/2024-05-20_18-43-00.jpg)
    
    - 실습
        
        ```kotlin
        @Composable
        fun MainScreen3() {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.size(width = 400.dp, height = 200.dp)
            ) {
                TextCell(text = "1")
                TextCell(text = "2")
                TextCell(text = "3")
            }
        }
        ```
        
        ![2024-05-20_18-44-28.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/81a143c7-86d6-4e8b-a037-9b8d8635192a/2024-05-20_18-44-28.jpg)
        
    

## 5. Row, Column 스코프 모디파이어

- Row, Column의 자식들은 부모듸 스코프 안에 있다고 말함
- 두 스코프는 추가 모디파이어 함수를 제공 → 자식의 동작, 형태 변경 가능
- ColumnScope
    
    ![2024-05-20_18-51-43.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/6a19ebff-1360-4202-9d42-07db6b1467a8/2024-05-20_18-51-43.jpg)
    
- RowScope
    
    ![2024-05-20_18-51-50.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/f0f54d84-5e70-41f4-9765-fe5e43535f8d/2024-05-20_18-51-50.jpg)
    
- 정렬 실습
    
    ```kotlin
    fun MainScreen4() {
        Row(
            modifier = Modifier.height(200.dp)
        ) {
            TextCell(text = "1", Modifier.align(Alignment.Top))
            TextCell(text = "2", Modifier.align(Alignment.CenterVertically))
            TextCell(text = "3", Modifier.align(Alignment.Bottom))
        }
    }
    ```
    
    ![2024-05-20_19-01-06.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/41369d81-ee82-42c8-91e4-930ca3f418bb/2024-05-20_19-01-06.jpg)
    

## 6. 베이스라인 정렬

- 베이스라인 정렬 - 글꼴 크기가 다른 텍스트 콘텐츠 정렬
- `Modifier.alignByBaseline()` 또는 `Modifier.alignBy(FirstBaseline)` 활용
- 실습
    
    ```kotlin
    @Composable
    fun MainScreen5() {
        Row {
            Text(
                text = "Large Text",
                Modifier.alignByBaseline(),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Small Text",
                Modifier.alignByBaseline(),
                fontSize = 30.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
    ```
    
    ![2024-05-20_19-12-13.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/32391a84-6cf7-4b58-b832-27ba27c7c2fc/2024-05-20_19-12-13.jpg)
    
- 여러 줄의 텍스트의 경우 `Modifier.alignBy(LastBaseline)` 활용

- 특정 자식의 정렬에 오프셋 적용 가능
    
    ```kotlin
    @Composable
    fun MainScreen5() {
        Row {
            Text(
                text = "Large Text\nMore Text",
                Modifier
                    .alignBy(FirstBaseline)
                    .background(Color.Green),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Small Text",
                Modifier
                    .paddingFrom(
                        alignmentLine = FirstBaseline,
                        before = 80.dp,
                        after = 20.dp
                    )
                    .background(Color.Gray),
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
    ```
    
    ![2024-05-20_19-17-42.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/07682162-36a7-410c-8d54-8b1d1bf4758a/2024-05-20_19-17-42.jpg)
    

## 7. 스코프 모디파이어 가중치

- Weight Modifier를 이용하면 각 자식의 폭은 그 형제들을 기준으로 상대적으로 지정할 수 있음
- 0.0~1.0
    
    ```kotlin
    @Composable
    fun MainScreen3() {
        Row {
            TextCell(text = "1", Modifier.weight(weight = 0.2f, fill = true))
            TextCell(text = "2", Modifier.weight(weight = 0.4f, fill = true))
            TextCell(text = "3", Modifier.weight(weight = 0.3f, fill = true))
        }
    }
    ```
    
    ![2024-05-20_22-55-55.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/4b57598c-502a-4303-b2bc-8f3dab8be3a5/2024-05-20_22-55-55.jpg)
