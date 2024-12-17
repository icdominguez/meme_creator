package com.icdominguez.icdominguez.memecreator.newmeme

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.icdominguez.icdominguez.memecreator.ui.theme.Impact

@Composable
fun BorderedText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: Float,
    fontWeight: FontWeight = FontWeight.Normal,
    borderColor: Color = Color.Black,
    textColor: Color = Color.White,
    fontFamily: FontFamily? = Impact,
) {
    val style = TextStyle(
        fontFamily = Impact,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Top,
            trim = LineHeightStyle.Trim.None
        ),
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
        fontSize = fontSize.sp,
        fontWeight = fontWeight
    )

    Box(
        modifier = modifier
    ) {
        Text(
            fontFamily = fontFamily,
            text = text,
            color = borderColor,
            style = style.copy(
                drawStyle = Stroke(
                    width = 5f
                )
            ),
        )
        Text(
            text = text,
            color = textColor,
            style = style
        )
    }
}