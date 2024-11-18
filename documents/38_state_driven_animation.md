## 1. 상태 함수로서의 애니메이션

- 상태 변화에 따라 애니메이션 효과를 이용할 수 있음
- 상태 주도 애니메이션 : animate*AsState
    - 와일드카드 문자(*)로 해당 애니메이션을 트리거하는 상태 유형으로 대체
    - 이 함수들은 변경 결과를 하나의 상탯값으로 애니메이션 함
    - targetValue를 지정하고, 현재 상탯값에서 대상 상탯값으로 변경을 애니메이션으로 표시
    - animateColorAsState, animateFloatAsState, animateDpAsState..

- 함수들은 상탯값을 반환하며, 컴포저블의 프로퍼티로 활용

    ```kotlin
    var colorState by remember { mutableStateOf(BoxColor.Red) }
    
    val animatedColor: Color by animateColorAsState(
        targetValue = when (colorState) {
            BoxColor.Red -> Color.Magenta
            BoxColor.Magenta -> Color.Red
        },
        animationSpec = tween(4500)
    )
    ```

    ```kotlin
    Box(
        modifier = Modifier
            .padding(20.dp)
            .size(200.dp)
            .background(animatedColor)
    )
    ```

## 2. dp로 움직임 애니메이션 처리

- 컴포저블의 위치 변경을 애니메이션 처리

    ```kotlin
    @Composable
    fun MotionDemo() {
    
        val screenWidth = (LocalConfiguration.current.screenWidthDp.dp)
        var boxState by remember { mutableStateOf(BoxPosition.Start)}
        val boxSideLength = 70.dp
    
        val animatedOffset: Dp by animateDpAsState(
            targetValue = when (boxState) {
                BoxPosition.Start -> 0.dp
                BoxPosition.End -> screenWidth - boxSideLength
            }
            animationSpec = tween(500)
        )
    
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .offset(x = animatedOffset, y = 20.dp)
                    .size(boxSideLength)
                    .background(Color.Red)
            )
    
            Spacer(modifier = Modifier.height(50.dp))
    
            Button(
                onClick = {
                    boxState = when (boxState) {
                        BoxPosition.Start -> BoxPosition.End
                        BoxPosition.End -> BoxPosition.Start
                    }
                },
                modifier = Modifier.padding(20.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Move Box")
            }
        }
    }
    ```

## 3. 스프링 효과 추가

- 스프링 효과
    - damping ratio, stiffness를 파라미터로 받음
    - 댐핑 비율은 튕김 효과가 감소하는 속도를 정의하며 부동 소수점 값으로 선언 (1.0은 튕김 없는 상태, 0.1은 가장 많이 튕기는 상태)
    - DampingRatioHighBouncy, StiffnessHigh 같은 상수 이용 가능
    - 강도는 스프링의 세기를 정의. 강도가 낮을수록 튕김 효과에 의한 움직임의 범위가 커짐

    ```kotlin
    animationSpec = spring(dampingRatio = DampingRatioHighBouncy, stiffness = StiffnessVeryLow)
    ```

## 4. 키프레임 다루기

- 키 프레임
    - 애니메이션 타임라인의 특정한 지점에 다양한 유지 시간이나 이징값 적용 가능
    - animationSpec 파라미터를 통해 애니메이션에 적용
    - keyframes() 함수를 이용해 지정
    - keyframes() 함수는 키 프레임 데이터를 포함한 람다를 전달받아 KeyframeSpec 인스턴스를 반환
    - 애니메이션을 완료하는 데 필요한 전체 유지 시간을 선언하는 것으로 시작 & 이후 전체 시간에 타임스탬프를 찍음

        ```kotlin
        animationSpec = keyframes { 
                durationMillis = 1000
                100.dp.at(10)
                110.dp.at(500)
                200.dp.at(700)
            }
        ```


- 이징을 추가할 수도 있음

    ```kotlin
    animationSpec = keyframes { 
            durationMillis = 1000
            100.dp.at(10).with(LinearEasing)
            110.dp.at(500).with(FastOutSlowInEasing)
            200.dp.at(700).with(LinearOutSlowInEasing)
        }
    ```

## 5. 여러 애니메이션 조합

- updateTransition()
    - 하나의 대상 상태를 기반으로 여러 애니메이션을 병렬로 실행 가능
    - targetState를 전달하면 Transition 인스턴스를 반환
    - targetState가 변경되며 이 트랜지션은 모든 자식 애니메이션을 동시에 실행
    - label로 트랜지션 식별 가능
    - Transition 클래스는 자식에 애니메이션을 추가하기 위해 이용되는 함수의 컬렉션을 포함
    - 애니메이션의 단위 타임에 따라 animate<Type>() 이라는 이름 규칙을 이용

    ```kotlin
    val transition = updateTransition(targetState = myState, label = "hi")
    
    val myAnimation: <Type> by transion.animate<Type>(
    		transitionSpec = {
    			// 애니메이션 스펙(tween, spring..)
    		}
    ) {
    	// 현재 상태를 기반으로 새로운 대상 상태를 식별할 코드
    }
    ```