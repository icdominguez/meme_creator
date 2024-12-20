package com.icdominguez.icdominguez.master_meme.presentation.screens.newmeme.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val colors = listOf("#FFFFFF", "#F2DE5B", "#FF532F", "#F83F3C", "#8329A5", "#4051AD", "#1296EB")

@Composable
fun SelectColorComponent(
    selectedColor: String,
    onColorClicked: (String) -> Unit = {},
) {
    LazyRow(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        items(colors) { color ->
            SelectColorItem(
                modifier = Modifier
                    .clickable { onColorClicked(color) },
                color = color,
                selected = color == selectedColor,
            )
        }
    }
}

@Preview
@Composable
private fun SelectColorComponentPreview() {
    SelectColorComponent(selectedColor = "#FFFFFF")
}

@Composable
fun SelectColorItem(
    modifier: Modifier = Modifier,
    color: String,
    selected: Boolean = false
) {
    Column(
        modifier = modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(if(selected) Color(android.graphics.Color.parseColor("#4A494C")) else Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(color = Color(android.graphics.Color.parseColor(color)))
        )
    }
}

@Preview
@Composable
private fun SelectColorItemPreview() {
    SelectColorItem(color = "#FFFFFF")
}