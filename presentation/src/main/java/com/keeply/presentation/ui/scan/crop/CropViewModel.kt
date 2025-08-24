package com.keeply.presentation.ui.scan.crop

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import com.keeply.presentation.ui.scan.navigation.ScanRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class CropViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
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
        // TODO: 실제 이미지 크롭 로직 구현
        // 현재는 임시로 원본 URI를 반환
        postSideEffect(CropSideEffect.CropCompleted(state.uri))
    }

}