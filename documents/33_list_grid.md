## 1. 기존 리스트 & ScrollState

- 기존 리스트 (Column, Row)
    - 모든 아이템을 한번에 표시 → 메모리 과부화 발생 가능
    - 기본적으로 스크롤되지 않음 → ScrollState 활용 필요
        
        ```kotlin
        val scrollState = rememberScrollState()
        
        Column(Modifier.verticalScroll(scrollState)) {
        		repeat(100) {
        				MyListItem()
        		}
        }
        ```
        

## 2. 지연 리스트

- 지연 리스트 (LazyColumn, LazyRow, LazyVerticalGrid )
    - 사용자에게 실제로 보이는 아이템만 생성
    - 사용자가 스크롤하면 표시 영역에서 벗어나는 아이템들은 파괴하고 리소스 확보, 표시되는 시점에 생성
    - 무한한 길이의 리스트로 성능 저하 없이 표시 가능

- item() 함수로 지연 리스트에 개별 아이템 추가
    
    ```kotlin
    LazyColumn {
    	item {
    		MyListItem()
    	}
    }
    ```
    
- item() 함수로 여러 아이템 한번에 추가
    
    ```kotlin
    LazyColumn {
    	item(1000) { index ->
    			Text("item $index")
    	}
    }
    ```
    
- itemsIndexed() 함수로 콘텐츠와 인덱스 값 함께 획득
    
    ```kotlin
    LazyColumn {
    	item {
    		itemIndexed(myList) { index, item ->
    				Text("$index = $item")
    		}
    	}
    }
    ```
    

## 3. 프로그래밍적 스크롤

- 프로그래밍적 스크롤
    - LazyListState 인스턴스 제공 함수 활용
        
        ```kotlin
        val listState = rememberLazyState()
        
        LazyColumn(state = listState) { }
        ```
        
    - LazyListState를 이용할 때는 재구성을 통해 기억되는 CoroutineScope 인스턴스에 접근 필요
        
        ```kotlin
        val coroutineScope = rememberCoroutineScope()
        
        coroutineScope.launch {
        	listState.animateScrollTo(listState.maxValue)
        }
        ```
        

- 스크롤 위치 반응
    
    ```kotlin
    // 현재 가장 처음에 보이는 리스트 아이템 인덱스 가져오기
    val firstVisible = listState.firstVisibleItemIndex
    
    if (firstVisible > 8) {
    		// 
    }
    ```
    

## 4. 스티키 헤더

- 스티키 헤더 : 현재 그룹이 스크롤되는 동안 헤더가 화면에서 계속 표시됨
- 리스트 콘텐츠를 groupBy() 함수로 저장해야 함
- ex.
    
    ```kotlin
    val phones = listOf("Apple 1", "Apple 2", "Galaxy 1", "Galaxy 2", ...)
    
    val groupedPhones = phones.groupBy { it.subStringBefore(' ') }
    ```
    
- forEach 문장을 이용해 그룹별로 스티키 헤더를 만들고 리스트 아이템으로 표시
    
    ```kotlin
    groupedPhones.forEach { (manufacturer, models) ->
    		stickyHeader {
    				Text(
    						text = manufacturer,
    				)
    		}
    		
    		items(models) { model ->
    				MyListItem(model)
    		}
    }
    ```
    

## 5. 반응형 그리드 레이아웃

- LazyVerticalGrid 컴포저블 활용
- 형태는 cells 파라미터를 통해 제어 & 이 파라미터는 adaptive mode, fixed mode로 설정 가능
    - 적응 모드 : 그리드가 이용할 수 있는 공간에 맞게 행과 열의 개수 계산
    - 고정 모드 : 포시할 행의 수를 전달하면 열의 폭을 동일한 크기로 조정

- 적응 모드 예시 (최소 폭 60dp)
    
    ```kotlin
    LazyVerticalGrid(
            cells = GridCells.Adaptive(minSize = 60.dp),
            state = rememberLazyListState(),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(30) { index ->
                Card(
                    backgroundColor = Color.Blue, modifier = Modifier
                        .padding(5.dp)
                        .fillMaxSize()
                ) {
                    Text(
                        text = "$index",
                        fontSize = 35.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    ```
    
    ![2024-11-18_01-35-48.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/c70a205a-2643-4ca5-8b4a-968745054084/2024-11-18_01-35-48.jpg)
