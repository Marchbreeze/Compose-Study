## 1. Room 핵심요소

- Repository(저장소) : 앱이 사용하는 모든 데이터 소스 직접 조작 (UI, ViewModel이 직접 접근 방지)
- Room DB : 내부 SQLite DB에 대한 인터페이스 제공 & DAO 접근할 수 있는 저장소 제공
- DAO(Data Access Object) : SQLite DB 안에서 데이터를 관리하는 SQL 구문 포함
- Entity : DB 내부 테이블에 대한 스키마 정의 - 데이터 타입 정의, 기본 키 식별 & 게터/세터 메서드 포함
- SQLite : 데이터를 저장하고 접근 제공

- 아키텍쳐

  ![2024-12-27_05-48-57.jpg](https://prod-files-secure.s3.us-west-2.amazonaws.com/edfd69d1-6c01-4d0c-9269-1bae8a4e3915/0aea2c1a-9c5f-44f9-8211-6d66c086784c/2024-12-27_05-48-57.jpg)

    1. 저장소는 Room DB와 상호작용을 해서 DB 인스턴스를 얻어 DAO 인스턴스에 대한 참조 획득
    2. 저장소는 Entity 인스턴스를 만들고 데이터를 설정한 뒤 DAO로 전달해 검색, 삽입 조작 실행
    3. 저장소는 DB에 삽입할 Entity를 DAO에 전달해 호출 & 응답으로 Entity 인스턴스 돌려받음
    4. DAO가 저장소에 반환할 결과를 가진 경우, Entity 객체로 패키징
    5. DAO는 Room DB와 상호작용을 해서 DB 조작 및 결과 처리
    6. Room DB는 내부 SQLite와의 모든 저수준 인터렉션 처리

## 2. Room 구현

### (1) Entity

- 테이블 필드를 나타내는 여러 변수와 게터/세터 메서드를 포함하는 기본 코틀린 클래스 선언
- Room 어노테이션을 활용해 SQL 구문 안에서 접근할 수 있도록 함

    ```kotlin
    import androidx.annotation.NonNull
    import androidx.room.ColumnInfo
    import androidx.room.Entity
    import androidx.room.PrimaryKey
    
    @Entity(tableName = "products")
    class Product {
    
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "productId")
        var id: Int = 0
    
        @ColumnInfo(name = "productName")
        var productName: String = ""
        var quantity: Int = 0
    
        constructor() {}
    
        constructor(id: Int, productname: String, quantity: Int) {
            this.productName = productname
            this.quantity = quantity
        }
        constructor(productname: String, quantity: Int) {
            this.productName = productname
            this.quantity = quantity
        }
    }
    ```

### (2) DAO

- 어노테이션으로 특정 SQL 구문과 저장소에서 호출되는 메서드 연결

    ```kotlin
    import androidx.lifecycle.LiveData
    import androidx.room.Dao
    import androidx.room.Insert
    import androidx.room.Query
    
    @Dao
    interface ProductDao {
    
        @Insert
        fun insertProduct(product: Product)
    
        @Query("SELECT * FROM products WHERE productName = :name")
        fun findProduct(name: String): List<Product>
    
        @Query("DELETE FROM products WHERE productName = :name")
        fun deleteProduct(name: String)
    
        @Query("SELECT * FROM products")
        fun getAllProducts(): LiveData<List<Product>>
    
    }
    ```

### (3) Room DB

- RoomDatabase 확장 - SQLite DM의 최상위 레이어로 동작
- 각 앱은 하나의 room DB 인스턴스만 가질 수 있음

    ```kotlin
    import android.content.Context
    import androidx.room.Database
    import androidx.room.Room
    import androidx.room.RoomDatabase
    
    @Database(entities = [(Product::class)], version = 1)
    abstract class ProductRoomDatabase: RoomDatabase()  {
    
        abstract fun productDao(): ProductDao
    
        companion object {
    
            private var INSTANCE: ProductRoomDatabase? = null
    
            fun getInstance(context: Context): ProductRoomDatabase {
                synchronized(this) {
                    var instance = INSTANCE
    
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            ProductRoomDatabase::class.java,
                            "product_database"
                        ).fallbackToDestructiveMigration()
                            .build()
    
                        INSTANCE = instance
                    }
                    return instance
                }
            }
        }
    }
    ```

### (4) Repository

- DAO 메서드 호출

    ```kotlin
    import androidx.lifecycle.LiveData
    import androidx.lifecycle.MutableLiveData
    import kotlinx.coroutines.*
    
    class ProductRepository(private val productDao: ProductDao) {
    
        val allProducts: LiveData<List<Product>> = productDao.getAllProducts()
        val searchResults = MutableLiveData<List<Product>>()
    
        private val coroutineScope = CoroutineScope(Dispatchers.Main)
    
        fun insertProduct(newproduct: Product) {
            coroutineScope.launch(Dispatchers.IO) {
                productDao.insertProduct(newproduct)
            }
        }
    
        fun deleteProduct(name: String) {
            coroutineScope.launch(Dispatchers.IO) {
                productDao.deleteProduct(name)
            }
        }
    
        fun findProduct(name: String) {
            coroutineScope.launch(Dispatchers.Main) {
                searchResults.value = asyncFind(name).await()
            }
        }
    
        private fun asyncFind(name: String): Deferred<List<Product>?> =
            coroutineScope.async(Dispatchers.IO) {
                return@async productDao.findProduct(name)
            }
    }
    ```

### (5) 뷰모델 접근

```kotlin
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) {

    private val repository: ProductRepository
    val allProducts: LiveData<List<Product>>
    val searchResults: MutableLiveData<List<Product>>

    init {
        val productDb = ProductRoomDatabase.getInstance(application)
        val productDao = productDb.productDao()
        repository = ProductRepository(productDao)

        allProducts = repository.allProducts
        searchResults = repository.searchResults
    }

    fun insertProduct(product: Product) {
        repository.insertProduct(product)
    }

    fun findProduct(name: String) {
        repository.findProduct(name)
    }

    fun deleteProduct(name: String) {
        repository.deleteProduct(name)
    }
}
```

- 안드로이드 스튜디오의 Database Inspector 도구 창 활용 시 검색 및 수정 가능