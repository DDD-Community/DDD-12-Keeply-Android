package com.keeply.presentation.ui.scan.crop

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

@Composable
fun CropOverlay(
    modifier: Modifier = Modifier,
    cropRect: Rect,
    onCropRectChange: (Rect) -> Unit,
    imageSize: Size
) {
    val density = LocalDensity.current
    val handleSize = with(density) { 30.dp.toPx() }
    var isDragging by remember(Unit) { mutableStateOf(false) }
    var dragHandle by remember(Unit) { mutableStateOf(DragHandle.NONE) }
    var dragStartRect by remember(Unit) { mutableStateOf(Rect.Zero) }
    var totalDragOffset by remember(Unit) { mutableStateOf(Offset.Zero) }
    var currentCropRect by remember(Unit) { mutableStateOf(Rect.Zero) }
    
    // cropRect 변경사항을 추적
    LaunchedEffect(cropRect) {
        currentCropRect = cropRect
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clipToBounds()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        dragStartRect = currentCropRect
                        totalDragOffset = Offset.Zero
                        dragHandle = getDragHandle(offset, currentCropRect, handleSize)
                        isDragging = dragHandle != DragHandle.NONE
                    },
                    onDragEnd = {
                        isDragging = false
                        dragHandle = DragHandle.NONE
                        // dragStartRect와 totalDragOffset은 초기화하지 않음
                        // 다음 드래그 시작 시에만 새로 설정됨
                    },
                    onDrag = { change, dragAmount ->
                        if (isDragging && dragHandle != DragHandle.NONE) {
                            totalDragOffset += dragAmount
                            val newRect = updateCropRect(
                                currentRect = dragStartRect,
                                dragHandle = dragHandle,
                                dragOffset = totalDragOffset,
                                imageSize = imageSize
                            )
                            onCropRectChange(newRect)
                        }
                    }
                )
            }
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawCropOverlay(
                cropRect = cropRect,
                imageSize = imageSize,
                canvasSize = size,
                handleSize = handleSize
            )
        }
    }
}

private fun DrawScope.drawCropOverlay(
    cropRect: Rect,
    imageSize: Size,
    canvasSize: Size,
    handleSize: Float
) {
    // 어두운 오버레이 (크롭 영역 제외)
    val overlayPath = Path().apply {
        addRect(androidx.compose.ui.geometry.Rect(Offset.Zero, canvasSize))
        addRect(cropRect)
        fillType = androidx.compose.ui.graphics.PathFillType.EvenOdd
    }
    
    drawPath(
        path = overlayPath,
        color = Color.Black.copy(alpha = 0.5f)
    )
    
    // 크롭 영역 테두리
    drawRect(
        color = Color.White,
        topLeft = cropRect.topLeft,
        size = cropRect.size,
        style = Stroke(width = 2.dp.toPx())
    )
    
    // 그리드 라인 (3x3)
    val gridLineColor = Color.White.copy(alpha = 0.7f)
    val gridStroke = Stroke(width = 1.dp.toPx())
    
    // 세로 그리드 라인
    for (i in 1..2) {
        val x = cropRect.left + (cropRect.width * i / 3)
        drawLine(
            color = gridLineColor,
            start = Offset(x, cropRect.top),
            end = Offset(x, cropRect.bottom),
            strokeWidth = gridStroke.width
        )
    }
    
    // 가로 그리드 라인
    for (i in 1..2) {
        val y = cropRect.top + (cropRect.height * i / 3)
        drawLine(
            color = gridLineColor,
            start = Offset(cropRect.left, y),
            end = Offset(cropRect.right, y),
            strokeWidth = gridStroke.width
        )
    }
    
    // 모서리 핸들
    val handleColor = Color.White
    val handleStroke = Stroke(width = 3.dp.toPx())
    val handleLength = 36.dp.toPx()
    
    // 좌상단 핸들
    drawCornerHandle(
        center = cropRect.topLeft,
        handleLength = handleLength,
        color = handleColor,
        stroke = handleStroke,
        corner = Corner.TOP_LEFT
    )
    
    // 우상단 핸들
    drawCornerHandle(
        center = cropRect.topRight,
        handleLength = handleLength,
        color = handleColor,
        stroke = handleStroke,
        corner = Corner.TOP_RIGHT
    )
    
    // 좌하단 핸들
    drawCornerHandle(
        center = cropRect.bottomLeft,
        handleLength = handleLength,
        color = handleColor,
        stroke = handleStroke,
        corner = Corner.BOTTOM_LEFT
    )
    
    // 우하단 핸들
    drawCornerHandle(
        center = cropRect.bottomRight,
        handleLength = handleLength,
        color = handleColor,
        stroke = handleStroke,
        corner = Corner.BOTTOM_RIGHT
    )
    
    // 가장자리 핸들
    val edgeHandleLength = 20.dp.toPx()
    
    // 상단 가장자리 핸들
    drawEdgeHandle(
        center = Offset(cropRect.center.x, cropRect.top),
        handleLength = edgeHandleLength,
        color = handleColor,
        stroke = handleStroke,
        edge = Edge.TOP
    )
    
    // 하단 가장자리 핸들
    drawEdgeHandle(
        center = Offset(cropRect.center.x, cropRect.bottom),
        handleLength = edgeHandleLength,
        color = handleColor,
        stroke = handleStroke,
        edge = Edge.BOTTOM
    )
    
    // 좌측 가장자리 핸들
    drawEdgeHandle(
        center = Offset(cropRect.left, cropRect.center.y),
        handleLength = edgeHandleLength,
        color = handleColor,
        stroke = handleStroke,
        edge = Edge.LEFT
    )
    
    // 우측 가장자리 핸들
    drawEdgeHandle(
        center = Offset(cropRect.right, cropRect.center.y),
        handleLength = edgeHandleLength,
        color = handleColor,
        stroke = handleStroke,
        edge = Edge.RIGHT
    )
}

private fun DrawScope.drawCornerHandle(
    center: Offset,
    handleLength: Float,
    color: Color,
    stroke: Stroke,
    corner: Corner
) {
    val halfLength = handleLength / 2
    
    when (corner) {
        Corner.TOP_LEFT -> {
            // 수평선 (오른쪽)
            drawLine(
                color = color,
                start = center,
                end = Offset(center.x + halfLength, center.y),
                strokeWidth = stroke.width
            )
            // 수직선 (아래)
            drawLine(
                color = color,
                start = center,
                end = Offset(center.x, center.y + halfLength),
                strokeWidth = stroke.width
            )
        }
        Corner.TOP_RIGHT -> {
            // 수평선 (왼쪽)
            drawLine(
                color = color,
                start = center,
                end = Offset(center.x - halfLength, center.y),
                strokeWidth = stroke.width
            )
            // 수직선 (아래)
            drawLine(
                color = color,
                start = center,
                end = Offset(center.x, center.y + halfLength),
                strokeWidth = stroke.width
            )
        }
        Corner.BOTTOM_LEFT -> {
            // 수평선 (오른쪽)
            drawLine(
                color = color,
                start = center,
                end = Offset(center.x + halfLength, center.y),
                strokeWidth = stroke.width
            )
            // 수직선 (위)
            drawLine(
                color = color,
                start = center,
                end = Offset(center.x, center.y - halfLength),
                strokeWidth = stroke.width
            )
        }
        Corner.BOTTOM_RIGHT -> {
            // 수평선 (왼쪽)
            drawLine(
                color = color,
                start = center,
                end = Offset(center.x - halfLength, center.y),
                strokeWidth = stroke.width
            )
            // 수직선 (위)
            drawLine(
                color = color,
                start = center,
                end = Offset(center.x, center.y - halfLength),
                strokeWidth = stroke.width
            )
        }
    }
}

private fun DrawScope.drawEdgeHandle(
    center: Offset,
    handleLength: Float,
    color: Color,
    stroke: Stroke,
    edge: Edge
) {
    val halfLength = handleLength / 2
    
    when (edge) {
        Edge.TOP -> {
            // 수평선
            drawLine(
                color = color,
                start = Offset(center.x - halfLength, center.y),
                end = Offset(center.x + halfLength, center.y),
                strokeWidth = stroke.width
            )
        }
        Edge.BOTTOM -> {
            // 수평선
            drawLine(
                color = color,
                start = Offset(center.x - halfLength, center.y),
                end = Offset(center.x + halfLength, center.y),
                strokeWidth = stroke.width
            )
        }
        Edge.LEFT -> {
            // 수직선
            drawLine(
                color = color,
                start = Offset(center.x, center.y - halfLength),
                end = Offset(center.x, center.y + halfLength),
                strokeWidth = stroke.width
            )
        }
        Edge.RIGHT -> {
            // 수직선
            drawLine(
                color = color,
                start = Offset(center.x, center.y - halfLength),
                end = Offset(center.x, center.y + halfLength),
                strokeWidth = stroke.width
            )
        }
    }
}

private enum class Corner {
    TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
}

private enum class Edge {
    TOP, BOTTOM, LEFT, RIGHT
}

private enum class DragHandle {
    NONE, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, 
    TOP, BOTTOM, LEFT, RIGHT, CENTER
}

private fun getDragHandle(
    touchPoint: Offset,
    cropRect: Rect,
    handleSize: Float
): DragHandle {
    val tolerance = handleSize
    
    // 모서리 핸들 체크
    if (isPointNear(touchPoint, cropRect.topLeft, tolerance)) return DragHandle.TOP_LEFT
    if (isPointNear(touchPoint, cropRect.topRight, tolerance)) return DragHandle.TOP_RIGHT
    if (isPointNear(touchPoint, cropRect.bottomLeft, tolerance)) return DragHandle.BOTTOM_LEFT
    if (isPointNear(touchPoint, cropRect.bottomRight, tolerance)) return DragHandle.BOTTOM_RIGHT
    
    // 가장자리 핸들 체크
    if (abs(touchPoint.y - cropRect.top) < tolerance && 
        touchPoint.x >= cropRect.left && touchPoint.x <= cropRect.right) return DragHandle.TOP
    if (abs(touchPoint.y - cropRect.bottom) < tolerance && 
        touchPoint.x >= cropRect.left && touchPoint.x <= cropRect.right) return DragHandle.BOTTOM
    if (abs(touchPoint.x - cropRect.left) < tolerance && 
        touchPoint.y >= cropRect.top && touchPoint.y <= cropRect.bottom) return DragHandle.LEFT
    if (abs(touchPoint.x - cropRect.right) < tolerance && 
        touchPoint.y >= cropRect.top && touchPoint.y <= cropRect.bottom) return DragHandle.RIGHT
    
    // 중앙 영역 체크
    if (cropRect.contains(touchPoint)) return DragHandle.CENTER
    
    return DragHandle.NONE
}

private fun isPointNear(point: Offset, target: Offset, tolerance: Float): Boolean {
    return abs(point.x - target.x) <= tolerance && abs(point.y - target.y) <= tolerance
}

private fun updateCropRect(
    currentRect: Rect,
    dragHandle: DragHandle,
    dragOffset: Offset,
    imageSize: Size
): Rect {
    var newRect = when (dragHandle) {
        DragHandle.TOP_LEFT -> Rect(
            left = currentRect.left + dragOffset.x,
            top = currentRect.top + dragOffset.y,
            right = currentRect.right,
            bottom = currentRect.bottom
        )
        DragHandle.TOP_RIGHT -> Rect(
            left = currentRect.left,
            top = currentRect.top + dragOffset.y,
            right = currentRect.right + dragOffset.x,
            bottom = currentRect.bottom
        )
        DragHandle.BOTTOM_LEFT -> Rect(
            left = currentRect.left + dragOffset.x,
            top = currentRect.top,
            right = currentRect.right,
            bottom = currentRect.bottom + dragOffset.y
        )
        DragHandle.BOTTOM_RIGHT -> Rect(
            left = currentRect.left,
            top = currentRect.top,
            right = currentRect.right + dragOffset.x,
            bottom = currentRect.bottom + dragOffset.y
        )
        DragHandle.TOP -> currentRect.copy(top = currentRect.top + dragOffset.y)
        DragHandle.BOTTOM -> currentRect.copy(bottom = currentRect.bottom + dragOffset.y)
        DragHandle.LEFT -> currentRect.copy(left = currentRect.left + dragOffset.x)
        DragHandle.RIGHT -> currentRect.copy(right = currentRect.right + dragOffset.x)
        DragHandle.CENTER -> Rect(
            left = currentRect.left + dragOffset.x,
            top = currentRect.top + dragOffset.y,
            right = currentRect.right + dragOffset.x,
            bottom = currentRect.bottom + dragOffset.y
        )
        DragHandle.NONE -> currentRect
    }
    
    // 최소 크기 제한
    val minSize = 50f
    if (newRect.width < minSize) {
        if (dragHandle == DragHandle.LEFT || dragHandle == DragHandle.TOP_LEFT || dragHandle == DragHandle.BOTTOM_LEFT) {
            newRect = newRect.copy(left = newRect.right - minSize)
        } else {
            newRect = newRect.copy(right = newRect.left + minSize)
        }
    }
    
    if (newRect.height < minSize) {
        if (dragHandle == DragHandle.TOP || dragHandle == DragHandle.TOP_LEFT || dragHandle == DragHandle.TOP_RIGHT) {
            newRect = newRect.copy(top = newRect.bottom - minSize)
        } else {
            newRect = newRect.copy(bottom = newRect.top + minSize)
        }
    }
    
    // 이미지 경계 제한
    newRect = Rect(
        left = max(0f, min(newRect.left, imageSize.width - minSize)),
        top = max(0f, min(newRect.top, imageSize.height - minSize)),
        right = max(minSize, min(newRect.right, imageSize.width)),
        bottom = max(minSize, min(newRect.bottom, imageSize.height))
    )
    
    return newRect
}