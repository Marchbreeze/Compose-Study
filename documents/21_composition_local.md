## 1. CompositionLocal 이해하기

- CompositionLocal은 컴포저블 계층 트리 상위에서 선언된 상태를 하위 함수에서 이용하는 방법 제공
- 그러나, 기존처럼 해당 상태가 선언된 함수와 상태를 이용하는 함수 사이에 있는 모든 컴포저블에 상태 전달하지는 않음

- 기존의 경우:
    
    ![2024-05-14_01-48-32.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/49ec8d85-4b29-40f2-b9b5-8e9c76dc8f2a/2024-05-14_01-48-32.jpg)
    
    - 다음 계층에서 colorState 상태는 Composable1에 정의되어 있으며, 8에서만 이용됨
    - 해당 상태는 3이나 5에서는 필요하지 않지만, 8에 전달되기 위해 전달되어야 함
        
        ⇒ 해결책 : CompositionLocal
        
    
- CompositionLocal을 사용하는 경우: 중간 자식 노드에 상태를 전달하지 않고도 이용 가능
    
    ![2024-05-14_01-50-20.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/f4b04cb1-da7e-4171-9a7d-25d56d825e46/2024-05-14_01-50-20.jpg)
    
    - 값이 할당된 지점 아래의 트리 분기에서만 데이터 이용 가능
        - ex. 3이 호출되었을 때 상태에 값이 할당되는 경우, 1,2,4,6에서는 접근하지 못함
    - 특정 브랜치에만 상태가 국지적으로 저장됨 & 다른 하위 브랜치는 동일한 CompositionLocal 상태에 다른 값을 가질 수 있게 함
        - ex. 5는 7이 호출될 때 할당된 다른 색상값을 가질 수 있음
        

## 2. CompositionLocal 이용하기

- 상태 선언 위해 ProvidableCompositionalLocal 인스턴스 생성 필요
- compositionLocalOf() 또는 staticCompositionLocalOf() 함수 호출로 획득
- 두 함수 모두 특별한 할당을 하지 않았을 때 상태에 할당할 기본값을 정의하는 람다를 받음
    
    ```kotlin
    val LocalColor1 = compositionLocalOf {Color.Red}
    val LocalColor2 = staticCompositionLocalOf {Color.Red}
    ```
    

1. staticCompositionLocalOf()
    - 자주 변경되지 않는 상태값을 저장할 때 이용됨
    - 상태값 변경 시 해당 상태가 할당된 노드의 하위 노드들을 모두 재구성함

1. compositionLocalOf()
    - 변경이 잦은 상태를 다룰 때 이용됨
    - 현재 상태에 접근하는 컴포저블에 대해서만 재구성을 수행

- ProvidableCompositionalLocal 인스턴스에 값 할당 후 자식 컴포저블로 전달
    
    ```kotlin
    val color = Color.Blue
    
    CompositionLocalProvider(LocalColor provides color) {
    		Composable5()
    }
    ```
    
    - Composition 5의 모든 자손은 ProvidableCompositionalLocal 인스턴스의 현재 프로퍼티를 통해 CompositionLocal 상태에 접근할 수 있음
        
        ```kotlin
        val background = LocalColor.current
        ```
        

## 3. CompositionLocal 사용해보기

1. 상태 추가하기
    
    ```kotlin
    // 기기 모드에 따라 변경되는 color 상태 선언 (값 주기적으로 변경되지 않음)
    val LocalColor = staticCompositionLocalOf { Color.Blue }
    
    @Composable
    fun Composable1() {
        var color = if (isSystemInDarkTheme()) {
            Color.Green
        } else {
            Color.Blue
        }
        Column {
            Composable2()
    
            CompositionLocalProvider(LocalColor provides color) {
                Composable3()
            }
        }
    }
    ```
    
2. 상태에 접근하기
    
    ```kotlin
    @Composable
    fun Composable2() {
        Composable4()
    }
    
    @Composable
    fun Composable3() {
        Composable5()
    }
    
    @Composable
    fun Composable4() {
        Composable6()
    }
    
    @Composable
    fun Composable5() {
        Composable8()
    }
    
    @Composable
    fun Composable6() {
        Text(text = "Composable 6")
    }
    
    @Composable
    fun Composable8() {
        Text(text = "Composable 8", modifier = Modifier.background(LocalColor.current))
    }
    ```
    
    - 색상 상태를 Compoable8의 Text 컴포넌트에 할당

1. 디자인 테스트하기
    - 새로운 Preview 컴포저블 추가해서 확인
    
    ```kotlin
    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ComposeStudyTheme {
            Composable1()
        }
    }
    
    @Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    fun DarkModePreview() {
        ComposeStudyTheme {
            Composable1()
        }
    }
    ```
    
    ![2024-05-14_02-24-41.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/14f5374c-b2a6-434f-9f1a-a0c6461badb5/2024-05-14_02-24-41.jpg)
