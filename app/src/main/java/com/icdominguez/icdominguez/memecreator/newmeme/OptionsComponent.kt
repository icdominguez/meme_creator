package com.icdominguez.icdominguez.memecreator.newmeme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
        Spacer(modifier = Modifier.weight(1f))
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