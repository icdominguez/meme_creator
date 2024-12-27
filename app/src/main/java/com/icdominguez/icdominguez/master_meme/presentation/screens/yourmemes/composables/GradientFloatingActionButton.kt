package com.icdominguez.icdominguez.master_meme.presentation.screens.yourmemes.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GradientFloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

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

    Box(
        modifier = modifier
            .padding(
                bottom = 32.dp,
                end = 16.dp,
            )
            .background(
                brush = brush,
                shape = RoundedCornerShape(12.dp)
            )
            .size(56.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null,
            tint = Color.Black,
        )
    }
}

@Composable
@Preview
fun GradientFloatingActionButtonPreview() {
    GradientFloatingActionButton()
}