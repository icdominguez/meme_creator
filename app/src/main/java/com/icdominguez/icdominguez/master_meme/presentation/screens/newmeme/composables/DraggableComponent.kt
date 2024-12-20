package com.icdominguez.icdominguez.master_meme.presentation.screens.newmeme.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.icdominguez.icdominguez.master_meme.R
import com.icdominguez.icdominguez.master_meme.presentation.model.TextMeme

@Composable
fun DraggableComponent(
    modifier: Modifier = Modifier,
    memeTemplateId: Int,
    items: List<TextMeme>,
    onRemoveTextButtonClicked: (TextMeme) -> Unit,
    onTextTapped: (TextMeme) -> Unit,
    selectedMeme: TextMeme? = null,
    onTextSwaped: (TextMeme) -> Unit,
    onTextDoubleTapped: (TextMeme) -> Unit,
) {
    val boxSize = remember { mutableStateOf(Size.Zero) }

    Box(
        modifier = modifier
            .background(Color.Red)
            .onSizeChanged { size ->
                boxSize.value = Size(size.width.toFloat(), size.height.toFloat())
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
            //val meme = selectedMeme.takeIf { it?.id == textMeme.id } ?: textMeme
            if (selectedMeme?.id == textMeme.id) {
                SwipableComponent(
                    boxSize = boxSize.value,
                    onRemoveTextClicked = { onRemoveTextButtonClicked(selectedMeme) },
                    onTextTapped = { onTextTapped(selectedMeme) },
                    selectedTextMeme = selectedMeme,
                    onTextSwaped = { onTextSwaped(it) },
                    onTextDoubleTapped = { onTextDoubleTapped(it) },
                    isSelected = true,
                )
            } else {
                SwipableComponent(
                    boxSize = boxSize.value,
                    onRemoveTextClicked = { onRemoveTextButtonClicked(textMeme) },
                    onTextTapped = { onTextTapped(textMeme) },
                    selectedTextMeme = textMeme,
                    onTextSwaped = { onTextSwaped(it) },
                    onTextDoubleTapped = { onTextDoubleTapped(it) },
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
        onTextDoubleTapped = {}
    )
}