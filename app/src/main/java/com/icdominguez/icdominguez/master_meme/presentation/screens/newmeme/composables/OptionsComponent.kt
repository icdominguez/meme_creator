package com.icdominguez.icdominguez.master_meme.presentation.screens.newmeme.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.icdominguez.master_meme.R

@Composable
fun OptionsComponent(
    onSaveMemeButtonClicked: () -> Unit = {},
    onAddTextButtonClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(34.dp)
    ) {
        Row {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.arrow_undo),
                    contentDescription = null,
                    tint = Color(android.graphics.Color.parseColor("#524C58")),
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.arrow_redo),
                    contentDescription = null,
                    tint = Color(android.graphics.Color.parseColor("#524C58")),
                )
            }
        }
        AddTextButton { onAddTextButtonClicked() }
        SaveMemeButton { onSaveMemeButtonClicked() }
    }
}

@Composable
@Preview(showBackground = true)
fun OptionsComponentPreview() {
    OptionsComponent(
        onSaveMemeButtonClicked = {},
        onAddTextButtonClicked = {}
    )
}