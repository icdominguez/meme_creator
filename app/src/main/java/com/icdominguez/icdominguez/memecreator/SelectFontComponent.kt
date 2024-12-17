package com.icdominguez.icdominguez.memecreator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icdominguez.icdominguez.memecreator.newmeme.BorderedText
import com.icdominguez.icdominguez.memecreator.ui.theme.Impact
import com.icdominguez.icdominguez.memecreator.ui.theme.Roboto

data class CustomFont(
    val fontFamily: FontFamily,
    val fontName: String,
    val text: String,
    val bordered: Boolean = true
)

@Composable
fun SelectFontComponent(
    fonts: List<CustomFont>
) {
    LazyRow {
        items(fonts) { font ->
            SelectFontItem(customFont = font)
        }
    }
}

@Preview
@Composable
private fun SelectFontComponentPreview() {
    SelectFontComponent(
        listOf(
            CustomFont(
                fontFamily = Impact,
                fontName = "Impact",
                text = "Good"
            ),
            CustomFont(
                fontFamily = Roboto,
                fontName = "Roboto",
                text = "Good",
            ),
            CustomFont(
                fontFamily = Roboto,
                fontName = "Shadowed",
                text = "GOOD"
            ),
            CustomFont(
                fontFamily = Impact,
                fontName = "Stroke",
                text = "GOOD",
                bordered = true
            )
        )
    )
}

@Composable
fun SelectFontItem(customFont: CustomFont) {
    Column(
        modifier = Modifier
            .padding(all = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(customFont.bordered) {
            BorderedText(
                text = customFont.text,
                fontSize = 28.sp.value,
                fontFamily = customFont.fontFamily,
            )
        } else {
            Box {
                Text(
                    text = customFont.text,
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = customFont.fontFamily,
                        fontSize = 28f.sp,
                    )
                )
            }
        }

        Text(
            text = customFont.fontName,
            fontFamily = customFont.fontFamily,
            color = Color.White,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectFontItemPreview() {
    SelectFontItem(
            CustomFont(
                fontFamily = Impact,
                fontName = "Impact",
                text = "Good"
            )
    )
}