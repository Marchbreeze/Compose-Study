## 1. Canvas 컴포넌트

- Canvas 컴포넌트
    - 2D 그래픽을 그릴 수 있는 표면 제공 & 콘텐츠 상태 유지 및 관리
    - 자체의 스코프 가짐 (DrawScope)
        - 크기 정보와 현재 영역의 중앙점을 포함한 캔버스 영역 프로퍼티 접근 가능
        - 도형, 선, 경로를 그리거나, 삽입을 정의하거나, 회전하는 등의 작업 수행

- 선 그리기

    ```kotlin
    @Composable
    fun DrawLine() {
        Canvas(modifier = Modifier.size(300.dp)) {
            val height = size.height
            val width = size.width
    
            drawLine(
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = width, y = height),
                color = Color.Red,
                strokeWidth = 16.0f
            )
        }
    }
    ```

  ![2024-12-24_16-15-55.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/1f9f3fc5-94da-4a99-a256-3d38499da017/2024-12-24_16-15-55.jpg)


- 점선 그리기

    ```kotlin
            drawLine(
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = width, y = height),
                color = Color.Red,
                strokeWidth = 16.0f,
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(30f, 10f, 10f, 10f), 0f
                )
            )
    ```

  ![2024-12-24_16-17-20.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/09993af1-d967-4966-b91a-8e247b458cc8/2024-12-24_16-17-20.jpg)


- 사각형 그리기

    ```kotlin
    @Composable
    fun DrawRect() {
        Canvas(modifier = Modifier.size(300.dp)) {
            val size = Size(width = 200.dp.toPx(), height = 100.dp.toPx())
            drawRect(
                color = Color.Blue,
                topLeft = Offset(x = 50.dp.toPx(), y = 50.dp.toPx()),
                size = size
            )
        }
    }
    ```

  ![2024-12-24_16-39-22.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/4a8f5715-89e1-4d32-a028-15035c430413/2024-12-24_16-39-22.jpg)


- inset() 함수로 컴포넌트 경계 수정

    ```kotlin
    @Composable
    fun DrawRect() {
        Canvas(modifier = Modifier.size(300.dp)) {
            val size = Size(width = 200.dp.toPx(), height = 100.dp.toPx())
            inset(50.dp.toPx(), 50.dp.toPx()) {
                drawRect(
                    color = Color.Blue,
                    size = size
                )
            }
        }
    }
    ```

  ![2024-12-24_16-39-22.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/4a8f5715-89e1-4d32-a028-15035c430413/2024-12-24_16-39-22.jpg)


- 둥근 모서리 사각형

    ```kotlin
    @Composable
    fun DrawRoundRect() {
        Canvas(modifier = Modifier.size(300.dp)) {
            val size = Size(width = 200.dp.toPx(), height = 100.dp.toPx())
            inset(50.dp.toPx(), 50.dp.toPx()) {
                drawRoundRect(
                    color = Color.Green,
                    size = size,
                    style = Stroke(width = 8.dp.toPx()),
                    cornerRadius = CornerRadius(
                        x = 20.dp.toPx(),
                        y = 20.dp.toPx()
                    )
                )
            }
        }
    }
    ```

  ![2024-12-24_16-58-29.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/5ef847a8-c3c3-48c2-a631-e4614aad5191/2024-12-24_16-58-29.jpg)


- 회전

    ```kotlin
    @Composable
    fun DrawRotatedRect() {
        Canvas(modifier = Modifier.size(300.dp)) {
            rotate(45f) {
                drawRect(
                    color = Color.Blue,
                    topLeft = Offset(200f, 200f),
                    size = size / 2f
                )
            }
        }
    }
    ```

  ![2024-12-24_17-01-29.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/376731a1-a25d-458c-a06a-180e29e2afd1/2024-12-24_17-01-29.jpg)


- 원

    ```kotlin
    @Composable
    fun DrawCircle() {
        Canvas(modifier = Modifier.size(300.dp)) {
            drawCircle(
                color = Color.Gray,
                center = center,
                radius = 120.dp.toPx()
            )
        }
    }
    ```

  ![2024-12-24_17-20-43.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/9f7946cb-8faf-4411-ac6e-50864e5e98ef/2024-12-24_17-20-43.jpg)


- 그라디언트 색상 (brush 활용)

    ```kotlin
    @Composable
    fun DrawGradientCircle() {
        Canvas(modifier = Modifier.size(300.dp)) {
            val colorList: List<Color>  = listOf(Color.Blue, Color.Black)
            val brush = Brush.horizontalGradient(
                colors = colorList,
                startX = 0.0f,
                endX = 300.dp.toPx(),
                tileMode = TileMode.Repeated
            )
    
            drawCircle(
                brush = brush,
                center = center,
                radius = 120.dp.toPx()
            )
        }
    }
    ```

  ![2024-12-24_17-26-25.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/1bed6a19-5c01-4351-bc5d-cc55b68aa220/2024-12-24_17-26-25.jpg)


- 부채꼴 그리기

    ```kotlin
    @Composable
    fun DrawArc() {
        Canvas(modifier = Modifier.size(300.dp)) {
            drawArc(
                color = Color.Gray,
                startAngle = 20f,
                sweepAngle = 90f,
                useCenter = true,
                size = size / 2f,
            )
        }
    }
    ```

  ![2024-12-24_17-29-59.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/830311b3-8328-4bcd-89cf-2928b079185a/2024-12-24_17-29-59.jpg)

    ```kotlin
    useCenter = false
    ```

  ![2024-12-24_17-30-32.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/009ba1db-0bee-4adb-88c4-eadf73edcc18/2024-12-24_17-30-32.jpg)


## 2. 경로 그리기

- 경로 : 캔버스 영역 안의 일련의 좌표들을 연결하는 선
    - Path 클래스 인스턴스에 저장
    - 정의된 경로를 drawPath() 함수에 전달해서 그림
    1. moveTo() 함수를 호출하고 시작 지점 정의
    2. 선 연결
        - lineTo() : 다음 x, y 좌표를 받음 (부모 canvas의 왼쪽 위 모서리 기준)
        - relativeLineTo() : 이전 위치를 기준으로 하는 좌표
        - 3차 베지어(Cubic), 2차 베지어(Quadratic) 곡선을 그릴수도 있음

- 구현

    ```kotlin
    @Composable
    fun DrawPath() {
        Canvas(modifier = Modifier.size(300.dp)) {
    
            val path = Path().apply {
                moveTo(0f, 0f)
                quadraticBezierTo(
                    50.dp.toPx(), 200.dp.toPx(),
                    300.dp.toPx(), 300.dp.toPx()
                )
                lineTo(270.dp.toPx(), 100.dp.toPx())
                quadraticBezierTo(60.dp.toPx(), 80.dp.toPx(), 0f, 0f)
                close()
            }
            drawPath(
                path = path,
                Color.Blue,
            )
        }
    }
    ```

  ![2024-12-24_17-51-04.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/8f21e4ea-5f6d-4642-90a8-347c0d95c1e0/2024-12-24_17-51-04.jpg)


## 3. 점 그리기

- drawPoints() : Offset 인스턴스 리스트로 지정한 위치마다 점 찍기 가능
- pointMode 파라미터 → 각 점을 개별적으로 찍을지 선으로 연결할지 선택 가능
- 구현

    ```kotlin
    @Composable
    fun DrawPoints() {
        Canvas(modifier = Modifier.size(300.dp)) {
    
            val height = size.height
            val width = size.width
    
            val points = mutableListOf<Offset>()
    
            for (x in 0..size.width.toInt()) {
                val y = (sin(x * (2f * PI / width))
                        * (height / 2) + (height / 2)).toFloat()
                points.add(Offset(x.toFloat(), y))
            }
            drawPoints(
                points = points,
                strokeWidth = 3f,
                pointMode = PointMode.Points,
                color = Color.Blue
            )
        }
    }
    ```

  ![2024-12-24_17-53-11.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/2460c9cb-af44-413f-b084-f95f8b08e17a/2024-12-24_17-53-11.jpg)