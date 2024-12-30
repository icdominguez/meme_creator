package com.icdominguez.icdominguez.master_meme.presentation.screens.newmeme.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toOffset
import androidx.compose.ui.unit.toSize
import com.icdominguez.icdominguez.master_meme.presentation.model.TextMeme

@Composable
fun SwipableComponent(
    boxSize: Size,
    onTextTapped: (TextMeme) -> Unit,
    onTextDoubleTapped: (TextMeme) -> Unit,
    onTextSwaped: (TextMeme) -> Unit,
    onRemoveTextClicked: () -> Unit,
    selectedTextMeme: TextMeme,
    isSelected: Boolean = false,
    onTextChanged: (String) -> Unit = {},
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController? = null,
) {
    val paddingSize = Size(
        width = with(LocalDensity.current) { 20.dp.toPx() },
        height = with(LocalDensity.current) { 18.dp.toPx() }
    )
    var hasFocus by remember { mutableStateOf(false) }

    val textMeasurer = rememberTextMeasurer()
    val isDragging = remember { mutableStateOf(false) }

    var textFieldValue by remember { mutableStateOf(TextFieldValue(selectedTextMeme.text)) }

    val textStyle = TextStyle(
        fontFamily = selectedTextMeme.typography.fontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Top,
            trim = LineHeightStyle.Trim.None
        ),
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
        fontSize = selectedTextMeme.fontSize.sp,
        fontWeight = selectedTextMeme.typography.fontWeight,
        color = Color(android.graphics.Color.parseColor(selectedTextMeme.color)),
        textAlign = TextAlign.Center
    )

    val textSize = remember {
        val textSize = textMeasurer.measure(
            text = selectedTextMeme.text,
            style = textStyle
        ).size

        mutableStateOf( Size(
            width = textSize.width + paddingSize.width,
            height = textSize.height + paddingSize.height
        ))
    }

    val firstPositionOffset = Offset(
        x = (boxSize.width - (textSize.value.width + paddingSize.width)) / 2,
        y = (boxSize.height - (textSize.value.height + paddingSize.height)) / 2
    )

    val textOffset = remember { mutableStateOf(firstPositionOffset) }

    fun calculateNewOffset(textOffset: Offset): IntOffset {
        val limitedX =
            textOffset.x.coerceIn(
                -(paddingSize.width),
                (boxSize.width + paddingSize.width) - textSize.value.width
            ).toInt()

        val limitedY =
            textOffset.y.coerceIn(
                -(paddingSize.height),
                (boxSize.height + paddingSize.height) - textSize.value.height
            ).toInt()

        return IntOffset(limitedX, limitedY)
    }

    Box(
        modifier = Modifier
            .onGloballyPositioned { layoutCoordinates ->
                textSize.value = layoutCoordinates.size.toSize()
            }
            .offset {
                calculateNewOffset(textOffset.value)
            }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    isDragging.value = true
                    textOffset.value = calculateNewOffset(textOffset.value + dragAmount).toOffset()
                    onTextSwaped(selectedTextMeme.copy(offset = textOffset.value))
                }
            }
    ) {
        Box(
            modifier = Modifier
                .padding(12.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { onTextTapped(selectedTextMeme.copy(text = textFieldValue.text)) },
                        onDoubleTap = { onTextDoubleTapped(selectedTextMeme.copy(text = textFieldValue.text)) }
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
            LaunchedEffect(selectedTextMeme.enabledToEdit) {
                if (selectedTextMeme.enabledToEdit) {
                    focusRequester.requestFocus()
                    keyboardController?.show()
                }
            }

            BasicTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused && !hasFocus) {
                            textFieldValue = textFieldValue.copy(
                                selection = TextRange(0, textFieldValue.text.length)
                            )
                            hasFocus = true
                        } else if (!focusState.isFocused) {
                            hasFocus = false
                        }
                    }
                    .width(IntrinsicSize.Min),
                cursorBrush = SolidColor(Color(android.graphics.Color.parseColor("#D0BCFF"))),
                value = textFieldValue,
                onValueChange = { newValue ->
                    textFieldValue = if (hasFocus) {
                        newValue
                    } else {
                        textFieldValue.copy(text = newValue.text)
                    }
                    onTextChanged(newValue.text)
                },
                textStyle = textStyle,
                decorationBox = { innerTextField ->
                    if (selectedTextMeme.typography.bordered) {
                        Text(
                            fontFamily = selectedTextMeme.typography.fontFamily,
                            text = selectedTextMeme.text,
                            color = Color.Black,
                            style = textStyle.copy(
                                drawStyle = Stroke(
                                    width = 5f
                                )
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                    innerTextField()
                },
                enabled = selectedTextMeme.enabledToEdit
            )

            LaunchedEffect(hasFocus) {
                if (hasFocus) {
                    textFieldValue = textFieldValue.copy(
                        selection = TextRange(0, textFieldValue.text.length)
                    )
                }
            }
        }

        if (isSelected) {
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
                    .clip(shape = CircleShape)
                    .align(Alignment.TopEnd)
                    .background(Color(android.graphics.Color.parseColor("#B3261E")))
                    .clickable { onRemoveTextClicked() }
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
        isSelected = true,
        focusRequester = FocusRequester(),
    )
}