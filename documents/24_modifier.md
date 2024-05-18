## 1. 모디파이어

- 컴포저블 : 실행 중인 앱에서의 형태나 동작을 정의하는 하나 이상의 파라미터를 받음
    - ex. Text 컴포저블 : fontsize = 40.sp, fontWeight = FontWeight,Bold
- 대부분의 내장 컴포저블 : 이런 특정 타입에 관련된 파라미터와 함께, 선택적으로 Modifier 파라미터를 받을 수 있음 → 좀 더 일반적인 방식으로 모든 컴포저블에 적용 가능

- Modifier 객체를 기반으로 구현 (컴포즈 내장 객체) → 적용될 수 있는 설정을 저장함
- 테두리, 패딩, 배경, 크기, 이벤트 핸들러, 제스처 등 다양한 프로퍼티 설정 가능
- Modifier를 선언한 뒤, 다른 컴포넌트에 전달해 형태나 행동을 변경할 수 있음

## 2. 모디파이어 만들기

- Modifier 인스턴스에 대한 메서드 호출을 연결해 여러 환경 설정을 한 오퍼레이션으로 적용
- 모피파이어를 선언한 뒤 파라미터로 받는 컴포저블에 전달
    
    ```kotlin
    @Composable
    fun DemoScreen2() {
        val modifier = Modifier
            .border(width = 2.dp, color = Color.Blue)
            .padding(all = 10.dp)
        Text(
            text = "Hello",
            modifier = modifier,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
    }
    
    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview2() {
        ComposeStudyTheme {
            DemoScreen2()
        }
    }
    ```
    
    ![2024-05-18_19-34-11.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/4485e799-5885-4e60-b695-f855acb060b1/2024-05-18_19-34-11.jpg)
    

## 3. 모디파이어의 연결 순서

- 연결 순서는 적용 결과에 영향을 미침
- 연결 순서를 변경해보면
    
    ```kotlin
    val modifier = Modifier
        .padding(all = 10.dp)
        .border(width = 2.dp, color = Color.Blue)
    ```
    
    ![2024-05-18_19-40-03.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/044a5bc5-940e-4d27-86a7-f4ce960e0558/2024-05-18_19-40-03.jpg)
    

## 4. 컴포저블에 모디파이어 지원 추가

- 규칙 : modifier는 함수의 파라미터 리스트 중 첫번째 선택적 파라미터여야 함
- 규칙 : 모디파이어 파라미터는 반드시 선택적이어야 함
- 규칙 : 해당 함수는 모디파이어 없이도 호출할 수 있어야 함 (기본값으로 빈 Modifier 인스턴스 지정)

- 이미지 컴포저블 활용하기
    
    ```kotlin
    @Composable
    fun CustomImage(image: Int, modifier: Modifier = Modifier) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            modifier
        )
    }
    ```
    
    ```kotlin
    @Composable
    fun DemoScreen2() {
        val modifier = Modifier
            .border(width = 2.dp, color = Color.Blue)
            .padding(all = 10.dp)
        Column(
            Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Hello",
                modifier = modifier,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomImage(
                    image = R.drawable.vacation,
                    Modifier
                        .padding(16.dp)
                        .width(270.dp)
                        .clip(shape = RoundedCornerShape(30.dp))
                )
        }
    }
    ```
    
    ![2024-05-18_20-08-33.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/29809b3e-9fe4-43e9-a2f6-4483e9c4fe87/2024-05-18_20-08-33.jpg)
    

## 5. 공통 내장 모디파이어

![2024-05-18_20-18-32.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/4c3965df-78f0-4db3-9d5e-903c68bf41a3/2024-05-18_20-18-32.jpg)

![2024-05-18_20-18-48.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/854566b3-2d75-47d8-a985-3de76a9f66c8/2024-05-18_20-18-48.jpg)

## 6. 모디파이어 조합하기

- then 키워드를 활용해 모디파이어 조합할 수 있음
    
    ```kotlin
    val firstModifier = Modifier
        .border(width = 2.dp, color = Color.Blue)
        .padding(all = 10.dp)
        
    val secondModifier = Modifier.height(100.dp)
    
    Text(
        text = "Hello",
        modifier = firstModifier.then(secondModifier),
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold
    )
    ```
    
    ![2024-05-18_20-23-11.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/db991832-4ccf-4cd9-acd8-77aaa430cad1/2024-05-18_20-23-11.jpg)
