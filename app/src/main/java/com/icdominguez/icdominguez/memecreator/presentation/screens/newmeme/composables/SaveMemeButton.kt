package com.icdominguez.icdominguez.memecreator.presentation.screens.newmeme.composables

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.icdominguez.memecreator.R

@Composable
fun SaveMemeButton(
    onCLick: () -> Unit
) {
    Button(
        shape = RoundedCornerShape(8.dp),
        onClick = { onCLick() }
    ) {
        Text(text = stringResource(R.string.save_meme_button))
    }
}

@Composable
@Preview(showBackground = true)
fun SaveMemeButtonPreview() {
    SaveMemeButton {  }
}