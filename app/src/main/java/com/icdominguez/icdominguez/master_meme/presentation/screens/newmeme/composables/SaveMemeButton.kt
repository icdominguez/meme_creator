package com.icdominguez.icdominguez.master_meme.presentation.screens.newmeme.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icdominguez.icdominguez.master_meme.R

@Composable
fun SaveMemeButton(
    onCLick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    var shouldDraw by remember { mutableStateOf(false) }

    val brush = if(isPressed) {
        Brush.linearGradient(
            colors = listOf(
                Color(0xFFE0D0FA),
                Color(0xFFAD90F1)
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                Color(0xFFEADDFF),
                Color(0xFFD0BCFE)
            )
        )
    }

    var textStyle by remember { mutableStateOf(TextStyle(
        fontSize = 14.sp,
        color = Color(android.graphics.Color.parseColor("#21005D")),
        fontWeight = FontWeight.Bold
    )) }

    Box(
        modifier = Modifier
            .background(
                brush = brush,
                shape = RoundedCornerShape(8.dp),
            )
            .clickable(interactionSource = interactionSource, indication = null) { onCLick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier
                .padding(
                    vertical = 10.dp,
                    horizontal = 16.dp
                ).drawWithContent {
                    if(shouldDraw) {
                        drawContent()
                    }
                },
            maxLines = 1,
            text = stringResource(R.string.save_meme_button),
            style = textStyle,
            softWrap = false,
            onTextLayout = { result ->
                if(result.didOverflowWidth) {
                    textStyle = textStyle.copy(
                        fontSize = textStyle.fontSize * 0.95
                    )
                } else {
                    shouldDraw = true
                }
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SaveMemeButtonPreview() {
    SaveMemeButton {  }
}