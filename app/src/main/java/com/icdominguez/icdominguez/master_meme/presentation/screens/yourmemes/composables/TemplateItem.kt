package com.icdominguez.icdominguez.master_meme.presentation.screens.yourmemes.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateItem(
    template: String,
    sheetState: SheetState,
    scope: CoroutineScope,
    onTemplateClicked: (String) -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            scope
                .launch { sheetState.hide() }
                .invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        onTemplateClicked(template)
                    }
                }
        },
    ) {
        AsyncImage(
            model = template,
            modifier = Modifier
                .fillMaxWidth()
                .height(176.dp)
                .clip(RoundedCornerShape(size = 8.dp)),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}