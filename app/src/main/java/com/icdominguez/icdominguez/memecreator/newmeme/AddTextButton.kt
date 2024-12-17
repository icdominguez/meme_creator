package com.icdominguez.icdominguez.memecreator.newmeme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.icdominguez.memecreator.R

@Composable
fun AddTextButton(
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier,
        shape = RoundedCornerShape(8.dp),
        onClick = { onClick() }
    ) {
        Text(text = stringResource(R.string.add_text_button))
    }
}

@Composable
@Preview(showBackground = true)
fun AddTextButtonPreview() {
    AddTextButton {  }
}