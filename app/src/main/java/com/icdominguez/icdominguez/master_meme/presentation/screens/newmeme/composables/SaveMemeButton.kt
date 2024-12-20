package com.icdominguez.icdominguez.master_meme.presentation.screens.newmeme.composables

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.icdominguez.master_meme.R

@Composable
fun SaveMemeButton(
    onCLick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = Color(android.graphics.Color.parseColor("#D0BCFE"))),
        shape = RoundedCornerShape(8.dp),
        onClick = { onCLick() }
    ) {
        Text(
            text = stringResource(R.string.save_meme_button),
            color = Color(android.graphics.Color.parseColor("#21005D"))
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SaveMemeButtonPreview() {
    SaveMemeButton {  }
}