package com.icdominguez.icdominguez.memecreator.presentation.screens.newmeme.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.icdominguez.icdominguez.memecreator.R

@Composable
fun SelectFontSizeComponent(
    onSliderValueChanged: (Float) -> Unit = {},
    fontSize: Float
) {

    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(painter = painterResource(R.drawable.font_small), null)

        Slider(
            modifier = Modifier
                .weight(1f),
            value = fontSize,
            onValueChange = {
                onSliderValueChanged(it)
            },
            valueRange = 20f..50f,
        )

        Icon(painter = painterResource(R.drawable.font_big), null)
    }
}