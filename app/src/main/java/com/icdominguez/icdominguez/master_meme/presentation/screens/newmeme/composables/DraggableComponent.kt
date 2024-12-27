package com.icdominguez.icdominguez.master_meme.presentation.screens.newmeme.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.icdominguez.icdominguez.master_meme.R
import com.icdominguez.icdominguez.master_meme.presentation.model.TextMeme

@Composable
fun DraggableComponent(
    modifier: Modifier = Modifier,
    memeTemplateId: Int,
    items: List<TextMeme>,
    onRemoveTextButtonClicked: () -> Unit = {},
    onTextTapped: (TextMeme) -> Unit = {},
    selectedMeme: TextMeme? = null,
    onTextSwaped: (TextMeme) -> Unit = {},
    onTextDoubleTapped: (TextMeme) -> Unit = {},
    onDraggableComponentClicked: () -> Unit = {},
    onTextChanged: (String) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val boxSize = remember { mutableStateOf(Size.Zero) }
    val integrationSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .background(Color.Red)
            .onSizeChanged { size ->
                boxSize.value = Size(size.width.toFloat(), size.height.toFloat())
            }
            .clickable(
                interactionSource = integrationSource,
                indication = null
            ) {
                onDraggableComponentClicked()
            },
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(memeTemplateId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        items.map { textMeme ->
            key(textMeme.id) {
                val textToShow = if(textMeme.id == selectedMeme?.id) selectedMeme else textMeme
                SwipableComponent(
                    boxSize = boxSize.value,
                    onRemoveTextClicked = { onRemoveTextButtonClicked() },
                    onTextTapped = {
                        onTextTapped(it)
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    },
                    selectedTextMeme = textToShow,
                    onTextSwaped = { onTextSwaped(it) },
                    onTextDoubleTapped = {
                        onTextDoubleTapped(it)
                        focusManager.clearFocus()
                    },
                    onTextChanged = { onTextChanged(it) },
                    isSelected = textMeme.id == selectedMeme?.id,
                    keyboardController = keyboardController,
                    focusRequester = focusRequester
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SwipableTextPreview() {
    DraggableComponent(
        items = emptyList(),
        memeTemplateId = R.drawable.arm_wrestle_agreement,
        onRemoveTextButtonClicked = {},
        onTextTapped = {},
        selectedMeme = TextMeme(),
        onTextSwaped = {},
        onTextDoubleTapped = {},
        onDraggableComponentClicked = {}
    )
}