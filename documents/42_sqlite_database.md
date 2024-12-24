---

## 1. 데이터베이스 테이블

- 데이터베이스 스키마
- 데이터의 특성 정의
- 전체 DB와 각 DB에 포함된 다양한 테이블 사이 관계 정의

- 열(column) : 테이블의 데이터 필드 (ex. 이름, 주소) - 특정 데이터 타입 포함하도록 정의
- 행(row, record, entry) : 테이블에 저장되는 새로운 데이터
- 기본 키 : 각 행을 고유하게 식별할 수 있는 하나 이상의 열 (필수)

## 2. SQLite

- SQL server : 내장형 라이브러리 형태
- SDLite 라이브러리에 포함된 함수를 호출해서 데이터베이스 조작 실행
- C언어로 작성되어, 안드로이드 SDK는 자바 기반의 wrapper를 이용해 인터페이스를 감쌈

- AVD에서 SQLite 사용
- AVD 에뮬레이터 인스턴스에 연결된 adb 셀 안에서 SQL 명령어 실행 가능
1. AVD 터미널 창을 열고 adb 커맨드라인 도구를 이용해 에뮬레이터 연결

```kotlin
adb -e shell
```

2. superuser 권한

```kotlin
Generic_x86:/ su
root@android:/ #
```

3. /data/data 디렉토리로 이동해 SQLite 실험을 해볼 하위 디렉토리 생성

```kotlin
cd /data/data
mkdir com.example.dbexample
cd com.example.dbexample
mkdir databases
cd databases
```

4. 인터렉티브 SQLite 도구 실행

```kotlin
sqlite3 ./mydatabase.db
```

5. 명령
- 테이블 리스트 확인

```kotlin
.tables
```

- 레코드 삽입

```kotlin
insert into contacts (name, address, phone) values ("Kim", "Korea", 12);
```

- 행 추출

```kotlin
select * from contracts;
```

- 특정 행 추출

```kotlin
select * from contracts where name="Kim";
```

- 인터렉티브 종료

```kotlin
.exit
```


- 안드로이드 : Room 퍼시스턴스 라이브러리 제공 → 아키텍쳐의 권고사항 지킬 수 있음