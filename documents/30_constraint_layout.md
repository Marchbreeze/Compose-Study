### (1) ConstraintLayout 호출하기

다른 레이아웃 컴포저블과 마찬가지

```kotlin
ConstraintLayout(Modifier.size(width = 200.dp, height = 300.dp)
												 .background(Color.Green)) {
		// 자식												 
}
```

### (2) 참조 만들고 할당하기

- 제약이 존재하지 않으면 ConstraintLayout의 컴포저블 자식은 콘텐츠 영역의 왼쪽 위 모서리에 배치됨 → 제약 받으려면 참조를 할당해야

- 참조 생성하기
    
    ```kotlin
    val text1 = createRef()
    val (text1, text2, btn) = createRefs()
    ```
    
- 참조 할당하기
    
    ```kotlin
    ConstraintLayout {
    		val text1 = createRef()
    		Text("hello", modifier = Modifier.constrainAs(text1) {
    				// 제약 설정
    		})
    }
    ```
    

### (3) 제약 추가하기

- linkTo() 함수 호출을 통해 constrainAs()의 후행 람다 안에서 선언
    
    ```kotlin
    ConstraintLayout {
    		val text1 = createRef()
    		Text("hello", modifier = Modifier.constrainAs(text1) {
    				// 제약 설정
    				top.linkTo(parent.top, bias = 0.8f)
    				bottom.linkTo(parent,bottom. margin = 16.dp)
    				linkTo(btn1.end, btn2.start)
    				centerHorizontallyTo(parent)
    				centerAround(text2.end)
    		})
    }
    ```
    

### (4) 프로젝트 실습

1. 라이브러리 추가 필요
    
    ```kotlin
    // libs.versions.toml
    androidx-constraint-compose = {group = "androidx.constraintlayout", name = "constraintlayout-compose", version.ref = "constraintCompose"}
    ```
    
    ```kotlin
    // build.gradble.kts
    implementation(libs.androidx.constraint.compose)
    ```
    
2. 컴포저블 추가
    
    ```kotlin
    @Composable
    fun MyButton(text: String, modifier: Modifier = Modifier) {
        Button(
            onClick = { },
            modifier = modifier
        ) {
            Text(text = text)
        }
    }
    
    @Composable
    fun MainScreen9() {
        ConstraintLayout(Modifier.size(200.dp, 200.dp)) {
            val (button1, button2, button3) = createRefs()
            MyButton(text = "Button1", Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 60.dp)
                start.linkTo(parent.start, margin = 30.dp)
            })
        }
    }
    ```
    
    ![2024-05-21_23-20-03.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/118be629-7c6d-47bf-9e14-7726df635c6e/2024-05-21_23-20-03.jpg)
    
3. 반대 제약
    
    ```kotlin
    start.linkTo(parent.start)
    end.linkTo(parent.end)
    ```
    
    ```kotlin
    linkTo(parent.start, parent.end)
    ```
    
    ```kotlin
    centerHorizontallyTo(parent)
    ```
    
    ![2024-05-21_23-21-56.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/b9259a29-7b9c-481d-ba8e-cd2180929b06/2024-05-21_23-21-56.jpg)
    
4. 컴포넌트 간 제약
    
    ```kotlin
        ConstraintLayout(Modifier.size(200.dp, 200.dp)) {
            val (button1, button2, button3) = createRefs()
            MyButton(text = "Button1", Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 30.dp)
                centerHorizontallyTo(parent)
            })
            MyButton(text = "Button2", Modifier.constrainAs(button2) {
                top.linkTo(button1.bottom, margin = 30.dp)
                centerHorizontallyTo(parent)
            })
        }
    ```
    
    ![2024-05-21_23-24-24.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/3986a776-40c0-414f-8135-7b22a491f72e/2024-05-21_23-24-24.jpg)
    
5. 제약 편향
    
    ```kotlin
            MyButton(text = "Button2", Modifier.constrainAs(button2) {
                top.linkTo(button1.bottom, margin = 30.dp)
                linkTo(parent.start, parent.end, bias = 0.75f)
            })
    ```
    
    ![2024-05-21_23-25-58.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/d668a6c5-53ed-4d84-a954-da4b352b8630/2024-05-21_23-25-58.jpg)
    
6. 체인
    
    ```kotlin
    @Composable
    fun MainScreen9() {
        ConstraintLayout(Modifier.size(400.dp, 200.dp)) {
            val (button1, button2, button3) = createRefs()
            createHorizontalChain(button1, button2, button3)
            MyButton(text = "Button1", Modifier.constrainAs(button1) {
                centerVerticallyTo(parent)
            })
            MyButton(text = "Button2", Modifier.constrainAs(button2) {
                centerVerticallyTo(parent)
            })
            MyButton(text = "Button3", Modifier.constrainAs(button3) {
                centerVerticallyTo(parent)
            })
        }
    }
    ```
    
    ![2024-05-21_23-29-08.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/5837cca9-288f-4cbc-a102-6cc5152f8c7d/2024-05-21_23-29-08.jpg)
    
7. 가이드라인
    
    ```kotlin
    val guide = createGuidelineFromTop(fraction = 0.2f)
    
    MyButton(text = "Button3", Modifier.constrainAs(button3) {
        top.linkTo(guide)
    })
    ```
    
    ![2024-05-21_23-43-12.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/05a8738a-f6b5-4077-a452-ad837a0141b4/2024-05-21_23-43-12.jpg)
    
8. 배리어 활용
    
    ```kotlin
    val barrier = createBottomBarrier(button1, button3)
    
    MyButton(text = "Button2", Modifier.constrainAs(button2) {
        linkTo(button3.bottom, parent.bottom)
        height = Dimension.fillToConstraints
        top.linkTo(barrier)
    })
    ```
    
    ![2024-05-21_23-46-37.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/7c75a4a2-a6b4-4ac7-88d2-bd8d55cfc579/2024-05-21_23-46-37.jpg)
    
    ![2024-05-21_23-47-16.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/95f711ac-ffb6-4e49-8577-8fac5f82d8d4/2024-05-21_23-47-16.jpg)
    

### (5) 제약 집합을 이용해 제약 연결 끊기

- 제약 집합(constraint set)의 형태로도 제약 선언 가능
- 분리된 제약(decoupled constraints)를 ConstraintLayout에 전달하면 컴포저블 자식들에 제약 적용
    
    → 모디파이어 선언을 중복하지 않고 재사용할 수 있는 제약 집합 생성 가능
    
- 기존 제약 예시
    
    ```kotlin
    @Composable
    fun MainScreen10() {
        ConstraintLayout(Modifier.size(200.dp, 200.dp)) {
            val button1 = createRef()
    
            MyButton(text = "Button1", Modifier.constrainAs(button1) {
                linkTo(
                    parent.top, parent.bottom,
                    topMargin = 8.dp, bottomMargin = 8.dp
                )
                linkTo(
                    parent.start, parent.end,
                    startMargin = 8.dp, endMargin = 8.dp
                )
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            })
        }
    }
    ```
    
- ConstraintSet 활용 예시
    
    ```kotlin
    private fun myConstraintSet(margin: Dp): ConstraintSet {
        return ConstraintSet {
            val button1 = createRefFor("button1")
            constrain(button1) {
                linkTo(
                    parent.top, parent.bottom,
                    topMargin = margin, bottomMargin = margin
                )
                linkTo(
                    parent.start, parent.end,
                    startMargin = margin, endMargin = margin
                )
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        }
    }
    ```
    
    ```kotlin
    @Composable
    fun MainScreen10() {
        ConstraintLayout(Modifier.size(200.dp, 200.dp)) {
            MyButton(text = "Button1", Modifier.size(200.dp).layoutId("button1"))
        }
    }
    ```
    
    ![2024-05-22_00-01-07.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/188a78e9-d384-4112-9349-11ea683d1a40/2024-05-22_00-01-07.jpg)
