package com.icdominguez.icdominguez.master_meme.presentation.screens.newmeme.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.icdominguez.icdominguez.master_meme.R
import com.icdominguez.icdominguez.master_meme.ui.theme.SecondaryFixedDim

@Composable
fun SelectFontSizeComponent(
    onSliderValueChanged: (Float) -> Unit = {},
    fontSize: Float
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(R.drawable.font_small),
            contentDescription = null,
            tint = SecondaryFixedDim,
        )

        Slider(
            colors = SliderDefaults.colors(
                thumbColor = SecondaryFixedDim,
                activeTrackColor = SecondaryFixedDim,
                inactiveTickColor = SecondaryFixedDim,
            ),
            modifier = Modifier
                .weight(1f),
            value = fontSize,
            onValueChange = {
                onSliderValueChanged(it)
            },
            valueRange = 20f..50f,
        )

        Icon(
            painter = painterResource(R.drawable.font_big),
            contentDescription = null,
            tint = SecondaryFixedDim,
        )
    }
}