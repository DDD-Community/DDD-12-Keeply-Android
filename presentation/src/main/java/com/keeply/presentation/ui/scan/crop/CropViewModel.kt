package com.keeply.presentation.ui.scan.crop

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import com.keeply.presentation.ui.scan.navigation.ScanRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import java.io.File
import java.io.FileOutputStream
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

@HiltViewModel
class CropViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context
): ContainerHost<CropState, CropSideEffect>,ViewModel() {
    
    private val scanCrop = savedStateHandle.toRoute<ScanRoute.ScanCrop>()
    
    override val container: Container<CropState, CropSideEffect> = container(
        CropState(uri = URLDecoder.decode(scanCrop.url, StandardCharsets.UTF_8.toString()))
    )

    fun updateCropRect(newRect: Rect) = intent {
        reduce { 
            state.copy(cropRect = newRect)
        }
    }
    
    fun updateImageSize(size: Size) = intent {
        reduce {
            state.copy(imageSize = size)
        }
    }
    
    fun cropImage() = intent {
        try {
            val originalUri = state.uri.toUri()
            val cropRect = state.cropRect
            val imageSize = state.imageSize
            
            // 크롭 영역이 설정되지 않은 경우 원본 URI 반환
            if (cropRect == Rect.Zero || imageSize == Size.Zero) {
                postSideEffect(CropSideEffect.CropCompleted(state.uri))
                return@intent
            }
            
            // 원본 이미지 로드
            val inputStream = context.contentResolver.openInputStream(originalUri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            
            if (originalBitmap == null) {
                postSideEffect(CropSideEffect.CropCompleted(state.uri))
                return@intent
            }
            
            // ContentScale.Fit으로 인한 실제 표시 영역 계산
            val originalAspectRatio = originalBitmap.width.toFloat() / originalBitmap.height.toFloat()
            val displayAspectRatio = imageSize.width / imageSize.height
            
            val actualDisplayedImageSize = if (originalAspectRatio > displayAspectRatio) {
                // 이미지가 더 넓음 - width에 맞춰짐
                Size(
                    width = imageSize.width,
                    height = imageSize.width / originalAspectRatio
                )
            } else {
                // 이미지가 더 높음 - height에 맞춰짐
                Size(
                    width = imageSize.height * originalAspectRatio,
                    height = imageSize.height
                )
            }
            
            // 실제 표시되는 이미지 영역의 오프셋 계산
            val offsetX = (imageSize.width - actualDisplayedImageSize.width) / 2f
            val offsetY = (imageSize.height - actualDisplayedImageSize.height) / 2f
            
            // 크롭 영역을 실제 표시 이미지 좌표계로 조정
            val adjustedCropRect = Rect(
                left = cropRect.left - offsetX,
                top = cropRect.top - offsetY,
                right = cropRect.right - offsetX,
                bottom = cropRect.bottom - offsetY
            )
            
            // 실제 이미지 크기와 화면 표시 크기의 비율 계산
            val scaleX = originalBitmap.width.toFloat() / actualDisplayedImageSize.width
            val scaleY = originalBitmap.height.toFloat() / actualDisplayedImageSize.height
            
            // 크롭 영역을 실제 이미지 좌표로 변환
            val actualCropX = (adjustedCropRect.left * scaleX).toInt()
            val actualCropY = (adjustedCropRect.top * scaleY).toInt()
            val actualCropWidth = ((adjustedCropRect.right - adjustedCropRect.left) * scaleX).toInt()
            val actualCropHeight = ((adjustedCropRect.bottom - adjustedCropRect.top) * scaleY).toInt()
            
            // 경계 검사
            val safeX = max(0, min(actualCropX, originalBitmap.width - 1))
            val safeY = max(0, min(actualCropY, originalBitmap.height - 1))
            val safeWidth = max(1, min(actualCropWidth, originalBitmap.width - safeX))
            val safeHeight = max(1, min(actualCropHeight, originalBitmap.height - safeY))
            
            // 비트맵 크롭
            val croppedBitmap = Bitmap.createBitmap(
                originalBitmap,
                safeX,
                safeY,
                safeWidth,
                safeHeight
            )
            
            // 크롭된 이미지를 파일로 저장
            val croppedFile = File(context.cacheDir, "cropped_image_${System.currentTimeMillis()}.jpg")
            val outputStream = FileOutputStream(croppedFile)
            croppedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            outputStream.close()
            
            // 리소스 정리
            originalBitmap.recycle()
            croppedBitmap.recycle()
            
            // 크롭된 이미지 URI 반환
            postSideEffect(CropSideEffect.CropCompleted(croppedFile.toURI().toString()))
            
        } catch (e: Exception) {
            e.printStackTrace()
            // 오류 발생 시 원본 URI 반환
            postSideEffect(CropSideEffect.CropCompleted(state.uri))
        }
    }

}