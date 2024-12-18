package com.icdominguez.icdominguez.memecreator.presentation.screens.newmeme.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.icdominguez.memecreator.R

@Composable
fun EditTextExtendedComponent() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.font_size_button),
                contentDescription = null
            )
        }

        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.text_size_button),
                contentDescription = null
            )
        }

        Box(
            modifier = Modifier.size(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(R.drawable.color_picker),
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun EditTextExtendedComponentComponentPreview() {
    EditTextExtendedComponent()
}