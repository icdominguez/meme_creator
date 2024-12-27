package com.icdominguez.icdominguez.master_meme.presentation.screens.newmeme.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icdominguez.icdominguez.master_meme.R

@Composable
fun AddTextButton(
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val color = if(isPressed)
        Color(android.graphics.Color.parseColor("#0F0D13"))
    else Color(android.graphics.Color.parseColor("#1D1A22"))

    Box(
        modifier = Modifier
            .background(
                color = color,
                shape = RoundedCornerShape(8.dp),
            )
            .border(
                width = 1.dp,
                color = Color(android.graphics.Color.parseColor("#81798F")),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
              },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier
                .padding(
                    vertical = 10.dp,
                    horizontal = 16.dp
                ),
            maxLines = 1,
            text = stringResource(R.string.add_text_button),
            style = TextStyle(
                fontSize = 14.sp,
                color = Color(android.graphics.Color.parseColor("#EADDFF")),
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AddTextButtonPreview() {
    AddTextButton()
}