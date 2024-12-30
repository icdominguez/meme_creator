package com.icdominguez.icdominguez.master_meme.presentation.screens.newmeme.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.icdominguez.icdominguez.master_meme.R
import com.icdominguez.icdominguez.master_meme.ui.theme.SecondaryFixedDim

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectFontSizeComponent(
    onSliderValueChanged: (Float) -> Unit = {},
    fontSize: Float
) {
    val interactionSource = remember { MutableInteractionSource() }
    val ripple = rememberRipple(
        bounded = false,
        radius = 16.dp,
        color = MaterialTheme.colorScheme.secondary
    )

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
            valueRange = 16f..70f,
            interactionSource = interactionSource,
            thumb = {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .indication(interactionSource, ripple)
                        .hoverable(interactionSource),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondary)
                    )
                }
            },
            track = { sliderState ->
                val fraction by remember {
                    derivedStateOf {
                        (sliderState.value - sliderState.valueRange.start) /
                                (sliderState.valueRange.endInclusive - sliderState.valueRange.start)
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp, end = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth(fraction)
                            .height(1.dp)
                            .background(
                                color = MaterialTheme.colorScheme.secondary,
                            )
                    )
                    Box(
                        Modifier
                            .fillMaxWidth(1f)
                            .height(1.dp)
                            .background(
                                color = MaterialTheme.colorScheme.secondary,
                            )
                    )
                }
            }
        )

        Icon(
            painter = painterResource(R.drawable.font_big),
            contentDescription = null,
            tint = SecondaryFixedDim,
        )
    }
}