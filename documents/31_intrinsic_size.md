## 1. 내재적 측정값

- 컴포즈 : 재구성 작업 중 각 컴포넌트를 한번만 측정하도록 제한 → 레이아웃을 빠르고 효율적으로 렌더링함
- 부모 컴포저블은 자식을 측정하기 전부터 크기 정보를 알아야하는 경우, 다른 방법이 필요 ⇒ IntrinsicSize

- 부모 컴포저블 : IntrinsicSize 열거형의 Min, Max 값에 접근해서 크기 정보 획득
    - 가장 넓은(큰) 자식이 가질 수 있는 최대, 최소 정보 제공

        ```kotlin
        Column(Modifier.width(IntrinsicSize.Max)){
        // ...
        }
        ```

      → 가장 폭이 넓은 자식의 최대 가능 폭으로 Column의 폭 설정


- 일반적으로 모디파이어가 없는 경우, 레이아웃 컴포저블은 이용할 수 있는 모든 공간을 차지하는 크기로 설정됨

  → IntrinsicSize 활용 시 자식들의 공간 요구에 맞춰 크기 설정 가능

## 2. 내재적 최대, 최소 측정값

- 최대 및 최소의 반환값 (제약이 없는 경우)

  ![2024-11-14_17-16-51.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/6e2ea226-8f6a-41f3-8c51-ef24c7242f52/2024-11-14_17-16-51.jpg)

- 제약이 있는 경우

  ![2024-11-14_17-17-58.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/28c996ee-d399-4727-a4e7-89ba400cbcb5/2024-11-14_17-17-58.jpg)