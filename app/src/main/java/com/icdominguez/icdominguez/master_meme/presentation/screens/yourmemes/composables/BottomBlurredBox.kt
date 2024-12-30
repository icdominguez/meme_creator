package com.icdominguez.icdominguez.master_meme.presentation.screens.yourmemes.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomBlurredBox(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(82.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.9f),
                    )
                )
            ),
    )
}

@Composable
@Preview(showBackground = true)
fun BottomBlurredBoxPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        BottomBlurredBox(modifier = Modifier.align(Alignment.BottomCenter))
    }
}