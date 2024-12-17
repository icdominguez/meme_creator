package com.icdominguez.icdominguez.memecreator.newmeme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.icdominguez.memecreator.R

@Composable
fun EditTextComponent(
    onSliderValueChanged: (Float) -> Unit,
    onCloseButtonClicked: () -> Unit,
    onCheckButtonClicked: () -> Unit,
    selectedMeme: TextMeme,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = { onCloseButtonClicked() }
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(R.drawable.font_small), null)

            Slider(
                modifier = Modifier.weight(1f),
                value = selectedMeme.fontSize,
                onValueChange = {
                    onSliderValueChanged(it)
                },
                valueRange = 20f..50f
            )

            Icon(painter = painterResource(R.drawable.font_big), null)
        }

        IconButton(
            onClick = { onCheckButtonClicked() }
        ) {
            Icon(imageVector = Icons.Default.Check, contentDescription = null)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AddTextComponentPreview() {
    EditTextComponent(
        onCheckButtonClicked = {},
        onCloseButtonClicked = {},
        onSliderValueChanged = {},
        selectedMeme = TextMeme(),
    )
}