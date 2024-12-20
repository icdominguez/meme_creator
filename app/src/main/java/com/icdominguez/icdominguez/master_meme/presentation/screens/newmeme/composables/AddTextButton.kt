package com.icdominguez.icdominguez.master_meme.presentation.screens.newmeme.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.icdominguez.master_meme.R

@Composable
fun AddTextButton(
    onClick: () -> Unit = {}
) {
    OutlinedButton(
        border = BorderStroke(
            width = 1.dp,
            color = Color(android.graphics.Color.parseColor("#81798F"))
        ),
        shape = RoundedCornerShape(8.dp),
        onClick = { onClick() }
    ) {
        Text(
            text = stringResource(R.string.add_text_button),
            color = Color(android.graphics.Color.parseColor("#E6D8FF"))
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AddTextButtonPreview() {
    AddTextButton()
}