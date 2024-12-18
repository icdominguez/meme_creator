package com.icdominguez.icdominguez.memecreator.presentation.screens.newmeme.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.icdominguez.icdominguez.memecreator.presentation.model.TextMeme

@Composable
fun SwipableComponent(
    boxSize: Size,
    onRemoveTextClicked: () -> Unit,
    onTextTapped: () -> Unit,
    selectedTextMeme: TextMeme,
    onTextSwaped: (TextMeme) -> Unit,
    onTextDoubleTapped: (TextMeme) -> Unit,
    isSelected: Boolean = false,
) {
    val yPaddingToPixels = with(LocalDensity.current) { 20.dp.toPx() }
    val xPaddingToPixels = with(LocalDensity.current) { 18.dp.toPx() }

    val firstPositionOffset = Offset(
        x = selectedTextMeme.offset?.x ?: -xPaddingToPixels,
        y = selectedTextMeme.offset?.y ?: -yPaddingToPixels
    )

    val textOffset = remember { mutableStateOf(firstPositionOffset) }
    val textSize = remember { mutableStateOf(Size.Zero) }
    val isDragging = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .onGloballyPositioned { layoutCoordinates ->
                textSize.value = layoutCoordinates.size.toSize()
            }
            .offset {
                val newOffset = textOffset.value

                val limitedX =
                    newOffset.x.coerceIn(
                        -(xPaddingToPixels),
                        (boxSize.width + xPaddingToPixels) - textSize.value.width
                    )
                val limitedY =
                    newOffset.y.coerceIn(
                        -(yPaddingToPixels),
                        (boxSize.height + yPaddingToPixels) - textSize.value.height
                    )

                textOffset.value = Offset(limitedX, limitedY)

                IntOffset(
                    x = textOffset.value.x.toInt(),
                    y = textOffset.value.y.toInt()
                )
            }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    isDragging.value = true
                    val newOffset = textOffset.value + dragAmount

                    val limitedX =
                        newOffset.x.coerceIn(
                            -(xPaddingToPixels),
                            (boxSize.width + xPaddingToPixels) - textSize.value.width
                        )
                    val limitedY =
                        newOffset.y.coerceIn(
                            -(yPaddingToPixels),
                            (boxSize.height + yPaddingToPixels) - textSize.value.height
                        )

                    textOffset.value = Offset(limitedX, limitedY)
                    onTextSwaped(selectedTextMeme.copy(offset = textOffset.value))
                }
            }
    ) {
        Box(
            modifier = Modifier
                .padding(12.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { onTextTapped() },
                        onDoubleTap = { onTextDoubleTapped(selectedTextMeme) }
                    )
                }
                .border(
                    width = 0.5.dp,
                    shape = RoundedCornerShape(4.dp),
                    color = if (isSelected) Color.White else Color.Transparent
                )
                .padding(
                    vertical = 4.dp,
                    horizontal = 6.dp
                )
        ) {
            BorderedText(
                text = selectedTextMeme.text,
                fontSize = selectedTextMeme.fontSize,
                fontWeight = FontWeight.Normal,
                borderColor = Color.Black,
                textColor = Color.White,
            )
        }

        if (isSelected) {
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
                    .clip(shape = CircleShape)
                    .align(Alignment.TopEnd)
                    .background(Color(android.graphics.Color.parseColor("#B3261E")))
                    .clickable {
                        onRemoveTextClicked()
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SwipableTextComponent() {
    SwipableComponent(
        boxSize = Size.Zero,
        onRemoveTextClicked = {},
        onTextTapped = {},
        selectedTextMeme = TextMeme(fontSize = 28f),
        onTextSwaped = {},
        onTextDoubleTapped = {},
        isSelected = true
    )
}