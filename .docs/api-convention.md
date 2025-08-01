# API 구현 컨벤션

이 문서는 Keeply 프로젝트에서 API를 구현할 때 따라야 할 컨벤션을 정리한 문서입니다.

## 1. 계층별 구현 순서

새로운 API를 구현할 때는 다음 순서를 따릅니다:

1. **Data Layer**
   - DTO (Data Transfer Object) 생성
   - Service 인터페이스에 API 메서드 추가
   - Repository 구현체에 메서드 구현

2. **Domain Layer**
   - Repository 인터페이스에 메서드 정의
   - UseCase 생성

3. **Presentation Layer**
   - ViewModel에 로직 구현
   - UI 컴포넌트 연결

## 2. Data Layer 구현

### 2.1 DTO 구조

```kotlin
// Request DTO
@Serializable
data class CreateFolderRequest(
    val folderName: String,
    val color: String
)

// Response DTO
@Serializable
data class FolderResponse(
    val folderId: Long?,
    val folderName: String?,
    val color: String?
)

// List Response DTO
@Serializable
data class FolderListResponse(
    val folderList: List<FolderData>? = null
)

@Serializable
data class FolderData(
    val folderId: Long? = null,
    val folderName: String? = null,
    val color: String? = null
)
```

### 2.2 Service 인터페이스

```kotlin
interface FolderService {
    @POST("api/folders")
    suspend fun createFolder(
        @Body request: CreateFolderRequest
    ): BaseResponse<FolderResponse>

    @GET("api/folders")
    suspend fun getFolders(): BaseResponse<FolderListResponse>
}
```

### 2.3 Mapper 함수

도메인 모델로 변환할 때는 `default()` 확장 함수를 활용합니다:

```kotlin
fun FolderResponse?.toDomain(): Folder {
    return Folder(
        folderId = this?.folderId.default(),
        folderName = this?.folderName.default(),
        color = this?.color.default()
    )
}

fun FolderData.toDomain(): Folder {
    return Folder(
        folderId = folderId.default(),
        folderName = folderName.default(),
        color = color.default()
    )
}
```

### 2.4 Repository 구현

```kotlin
class FolderRepositoryImpl @Inject constructor(
    private val folderService: FolderService,
    private val json: Json
) : FolderRepository {
    
    override suspend fun createFolder(folderName: String, color: String): Flow<Folder> = flow {
        try {
            val response = folderService.createFolder(
                CreateFolderRequest(
                    folderName = folderName,
                    color = color
                )
            )
            
            if (response.success == true && response.response != null) {
                emit(response.response.toDomain())
            } else {
                throw Exception(response.reason)
            }
        } catch (e: HttpException) {
            throw e.toException(json)
        }
    }

    override suspend fun getFolders(): Flow<List<Folder>> = flow {
        try {
            val response = folderService.getFolders()
            
            if (response.success == true && response.response != null) {
                val folders = response.response.folderList?.map { it.toDomain() } ?: emptyList()
                emit(folders)
            } else {
                throw Exception(response.reason)
            }
        } catch (e: HttpException) {
            throw e.toException(json)
        }
    }
}
```

## 3. Domain Layer 구현

### 3.1 Repository 인터페이스

```kotlin
interface FolderRepository {
    suspend fun createFolder(folderName: String, color: String): Flow<Folder>
    suspend fun getFolders(): Flow<List<Folder>>
}
```

### 3.2 UseCase

```kotlin
class GetFoldersUseCase @Inject constructor(
    private val folderRepository: FolderRepository
) {
    suspend operator fun invoke(): Flow<List<Folder>> {
        return folderRepository.getFolders()
    }
}
```

## 4. Presentation Layer 구현

### 4.1 State 정의

```kotlin
@Immutable
data class FolderState(
    val folders: List<Folder> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
```

### 4.2 ViewModel 구현

```kotlin
@HiltViewModel
class FolderViewModel @Inject constructor(
    private val getFoldersUseCase: GetFoldersUseCase
): ContainerHost<FolderState, FolderSideEffect>, ViewModel() {
    override val container: Container<FolderState, FolderSideEffect> = container(FolderState())

    init {
        loadFolders()
    }

    private fun loadFolders() = intent {
        viewModelScope.launch {
            getFoldersUseCase()
                .onStart {
                    reduce { state.copy(isLoading = true) }
                }
                .catch { e ->
                    reduce { 
                        state.copy(
                            isLoading = false,
                            error = e.message
                        ) 
                    }
                }
                .collect { folders ->
                    reduce { 
                        state.copy(
                            isLoading = false,
                            folders = folders,
                            error = null
                        ) 
                    }
                }
        }
    }
}
```

## 5. 에러 처리

### 5.1 HTTP 에러 처리 확장 함수

```kotlin
@Serializable
private data class ErrorResponse(
    val success: Boolean? = null,
    val reason: String? = null
)

fun HttpException.getErrorMessage(
    json: Json,
    defaultMessage: String = "요청 처리에 실패했습니다."
): String {
    val errorBody = response()?.errorBody()?.string()
    return if (errorBody != null) {
        try {
            val errorResponse = json.decodeFromString<ErrorResponse>(errorBody)
            errorResponse.reason ?: "$defaultMessage (${code()})"
        } catch (e: Exception) {
            "$defaultMessage (${code()})"
        }
    } else {
        "$defaultMessage (${code()})"
    }
}

fun HttpException.toException(
    json: Json,
    defaultMessage: String = "요청 처리에 실패했습니다."
): Exception {
    return Exception(getErrorMessage(json, defaultMessage))
}
```

## 6. 네이밍 컨벤션

### 6.1 DTO 네이밍
- Request: `{기능명}Request` (예: CreateFolderRequest)
- Response: `{기능명}Response` (예: FolderResponse)
- List Response: `{기능명}ListResponse` (예: FolderListResponse)

### 6.2 UseCase 네이밍
- 조회: `Get{대상}UseCase` (예: GetFoldersUseCase)
- 생성: `Create{대상}UseCase` (예: CreateFolderUseCase)
- 수정: `Update{대상}UseCase` (예: UpdateFolderUseCase)
- 삭제: `Delete{대상}UseCase` (예: DeleteFolderUseCase)

### 6.3 Repository 메서드 네이밍
- 조회: `get{대상}` (예: getFolders)
- 생성: `create{대상}` (예: createFolder)
- 수정: `update{대상}` (예: updateFolder)
- 삭제: `delete{대상}` (예: deleteFolder)

## 7. Flow 사용 규칙

- 모든 Repository 메서드는 `Flow`를 반환합니다
- 단일 값도 `Flow<T>`로 반환합니다
- 에러는 `throw`로 처리하고, ViewModel에서 `catch`로 처리합니다

## 8. 화면 갱신 처리

폴더 생성 등의 작업 후 화면을 갱신해야 할 때:

```kotlin
// Navigation에서 결과 전달
navController.previousBackStackEntry?.savedStateHandle?.set("folder_created", true)

// 이전 화면에서 결과 확인
val folderCreated = navController.currentBackStackEntry?.savedStateHandle?.get<Boolean>("folder_created").default()

if (folderCreated) {
    viewModel.refreshFolders()
}
```

## 9. 타입 안전성

- 모든 DTO 필드는 nullable로 선언합니다
- 도메인 모델로 변환할 때 `default()` 확장 함수를 사용합니다
- ID 필드는 `Long` 타입을 사용합니다 (Int 대신)

## 10. 의존성 주입

- 모든 Repository, UseCase는 `@Inject` 어노테이션을 사용합니다
- ViewModel은 `@HiltViewModel` 어노테이션을 사용합니다
- 생성자 주입을 사용합니다