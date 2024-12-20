package com.icdominguez.icdominguez.master_meme.presentation.screens.newmeme.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.icdominguez.master_meme.R
import com.icdominguez.icdominguez.master_meme.presentation.model.TextMeme
import com.icdominguez.icdominguez.master_meme.ui.theme.SecondaryFixedDim

@Composable
fun EditTextExtendedComponent(
    selectedMeme: TextMeme,
    onCustomFontClicked: (CustomFont) -> Unit = {},
    onSliderValueChanged: (Float) -> Unit = {},
    onColorClicked: (String) -> Unit = {},
    onCloseButtonClicked: () -> Unit = {},
    onCheckButtonClicked: () -> Unit = {},
) {
    var isSelectFontComponentClicked by remember { mutableStateOf(true) }
    var isSelectColorComponentClicked by remember { mutableStateOf(false) }
    var isSelectFontSizeComponentClicked by remember { mutableStateOf(false) }

    if (isSelectFontComponentClicked) {
        SelectFontComponent(
            customFont = selectedMeme.typography,
            onCustomFontClicked = { onCustomFontClicked(it) }
        )
    }
    if (isSelectFontSizeComponentClicked) {
        SelectFontSizeComponent(
            onSliderValueChanged = { onSliderValueChanged(it) },
            fontSize = selectedMeme.fontSize
        )
    }
    if (isSelectColorComponentClicked) {
        SelectColorComponent(
            selectedColor = selectedMeme.color,
            onColorClicked = { onColorClicked(it) }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = { onCloseButtonClicked() }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = SecondaryFixedDim,
            )
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                Modifier
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(if(isSelectFontComponentClicked) Color(android.graphics.Color.parseColor("#2B2930")) else Color.Transparent)
                    .clickable {
                        isSelectFontComponentClicked = true
                        isSelectFontSizeComponentClicked = false
                        isSelectColorComponentClicked = false
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.font_size_button),
                    contentDescription = null
                )
            }

            Box(
                Modifier
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(if(isSelectFontSizeComponentClicked) Color(android.graphics.Color.parseColor("#2B2930")) else Color.Transparent)
                    .clickable {
                        isSelectFontComponentClicked = false
                        isSelectFontSizeComponentClicked = true
                        isSelectColorComponentClicked = false
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.text_size_button),
                    contentDescription = null
                )
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(if(isSelectColorComponentClicked) Color(android.graphics.Color.parseColor("#2B2930")) else Color.Transparent)
                    .clickable {
                        isSelectFontComponentClicked = false
                        isSelectFontSizeComponentClicked = false
                        isSelectColorComponentClicked = true
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(R.drawable.color_picker),
                    contentDescription = null
                )
            }
        }

        IconButton(
            onClick = { onCheckButtonClicked() }
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = SecondaryFixedDim,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun EditTextExtendedComponentComponentPreview() {
    EditTextExtendedComponent(
        selectedMeme = TextMeme()
    )
}