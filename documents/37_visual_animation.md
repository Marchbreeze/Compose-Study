## 1. 시각적 애니메이션

- 컴포저블이 나타나거나 사라질 때 활용

- 애니메이션 없이 버튼 나타내는 방법

    ```kotlin
    var boxVisible by remember { mutableStateOf(true) }
    
    val onClick = { newState: Boolean ->
        boxVisible = newState
    }
    
    ...
    
    	Row() {
    			CustomButton(test = "Show", targetState = true, onClick = onClick)
    			CustomButton(test = "Hide", targetState = true, onClick = onClick)
    	}
    	
    	if (boxVisible) {
    			Box(modifier = Modifier
    					.background(Color.Blue))
    	}
    ```

    - 버튼을 클릭하면 핸들러를 호출하고 새 state 값 전달

- 시각화 애니메이션 추가

    ```kotlin
    AnimatedVisibility(visible = boxVisible) {
    		Box(modifier = Modifier
    				.background(Color.Blue))
    }
    ```


## 2. 진입/이탈 애니메이션

- AnimatedVisibility 컴포저블의 자식들이 나타나고 사라질 때, enter, exit 파라미터로 애니메이션을 조절할 수 있음 (조합도 가능)
    - expandHorizontally()
    - expandVertically()
    - expandIn()
    - fadeIn()
    - fadeOut()
    - scaleIn()
    - scaleOut()
    - shrinkHorizontally()
    - …

    ```kotlin
    // 나타날 때 : 페이드인 & 수평으로 자르는 기법으로 나타남
    // 사라질 때 : 수직으로 슬라이드해서 사라짐
    AnimatedVisibility(
          visible = boxVisible,
          enter = fadeIn() + expandHorizontally(),
          exit = slideOutVertically()
      ) {
    		...
    }
    ```


## 3. 애니메이션 스팩 & 애니메이션 이징

- AnimationSpec 으로 유지 시간, 시작 지연, 스프링, 튕김 효과, 반복, 애니메이션 이징(애니메이션 속도 증가 또는 감소)을 포함한 설정 가능
- tween() 함수로 애니메이션 이징 지정 가능

    ```kotlin
    enter = fadeIn(
    		    animationSpec = repeatable(
    		        10,
    		        animation = tween(durationMillis = 2000),
    		        repeatMode = RepeatMode.Reverse
    		    )
    		)
    ```

- 미리 지정된 이징 사용도 가능

    ```kotlin
    tween(durationMillis = 2000, easing = LinearOutSlowInEasing)
    ```

    - 박스가 표시될 때, 대상 위치에 가까워짐에 따라 점점 느려짐

    ```kotlin
    tween(durationMillis = 2000, easing = CubicBezierEasing(0f, 1f, 0.5f, 1f))
    ```

    - 베지어 곡선 안의 4개 위치에서 애니메이션 속도를 변경

- 애니메이션 부모에 선언된 모션은 자식에도 적용
    - animateEnterExit() 모디파이어를 이용하면 자식별로 개별적인 애니메이션을 지정해서 적용 가능
    - 모디파이어의 애니메이션만 이용하고 싶다면 부모인 AnimatedVisibility 인스턴스에서EnterTransition.None, ExitTransition.None 지정

        ```kotlin
        Modifier.animateEnterExit(
          enter = slideInVertically(animationSpec = tween(durationMillis = 5500)),
          exit = slideOutVertically(animationSpec = tween(durationMillis = 5500))
        )
        ```


- 애니메이션 자동 시작
    - MutableTransitionState 인스턴스 활용

        ```kotlin
        val state = remember { MutableTransitionState(false)}
        state.apply { targetState = true }
        
        AnimatedVisibility(
              visibleState = state,
              ...
        ```


- 교차 페이딩
    - CrossFade() 로 한 컴포저블을 다른 컴포저블로 자연스럽게 대체

        ```kotlin
        Crossfade(
            targetState = boxVisible,
            animationSpec = tween(5000)
        ) { visible ->
            when (visible) {
                true -> CustomButton(
                    text = "Hide", targetState = false,
                    onClick = onClick, bgColor = Color.Red
                )
        
                false -> CustomButton(
                    text = "Show", targetState = true,
                    onClick = onClick, bgColor = Color.Magenta
                )
            }
        }
        ```