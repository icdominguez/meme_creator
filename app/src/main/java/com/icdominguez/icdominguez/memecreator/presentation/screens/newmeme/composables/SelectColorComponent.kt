package com.icdominguez.icdominguez.memecreator.presentation.screens.newmeme.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SelectColorComponent(colors: List<String>) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        items(colors) { color ->
            SelectColorItem(color)
        }
    }
}

@Preview
@Composable
private fun SelectColorComponentPreview() {
    SelectColorComponent(colors = listOf("#FFFFFF", "#F2DE5B", "#FF532F", "#F83F3C", "#8329A5", "#4051AD", "#1296EB"))
}

@Composable
fun SelectColorItem(color: String) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(color = Color(android.graphics.Color.parseColor(color)))
    )
}

@Preview
@Composable
private fun SelectColorItemPreview() {
    SelectColorItem(color = "#FFFFFF")
}