package com.icdominguez.icdominguez.master_meme.presentation.screens.newmeme.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.icdominguez.master_meme.R
import com.icdominguez.icdominguez.master_meme.presentation.model.TextMeme
import com.icdominguez.icdominguez.master_meme.ui.theme.SecondaryFixedDim

@Composable
fun OptionsComponent(
    onSaveMemeButtonClicked: () -> Unit = {},
    onAddTextButtonClicked: () -> Unit = {},
    onUndoButtonClicked: () -> Unit = {},
    onRedoButtonClicked: () -> Unit = {},
    undoItems: List<TextMeme> = emptyList(),
    redoItems: List<TextMeme> = emptyList(),
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(34.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center,
        ) {
            IconButton(
                onClick = { onUndoButtonClicked() },
                enabled = undoItems.isNotEmpty()
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_undo),
                    contentDescription = null,
                    tint = if(undoItems.isEmpty()) Color(0xFF524C58) else SecondaryFixedDim,
                )
            }
            IconButton(
                onClick = { onRedoButtonClicked() },
                enabled = redoItems.isNotEmpty()
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_redo),
                    contentDescription = null,
                    tint = if(redoItems.isEmpty()) Color(0xFF524C58) else SecondaryFixedDim,
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AddTextButton { onAddTextButtonClicked() }
        }

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            SaveMemeButton { onSaveMemeButtonClicked() }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun OptionsComponentPreview() {
    OptionsComponent()
}