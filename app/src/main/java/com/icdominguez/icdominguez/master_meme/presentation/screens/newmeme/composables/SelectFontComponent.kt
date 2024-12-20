package com.icdominguez.icdominguez.master_meme.presentation.screens.newmeme.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icdominguez.icdominguez.master_meme.ui.theme.Impact
import com.icdominguez.icdominguez.master_meme.ui.theme.Manrope
import com.icdominguez.icdominguez.master_meme.ui.theme.Roboto_Bold
import com.icdominguez.icdominguez.master_meme.ui.theme.Roboto_Thin

data class CustomFont(
    val fontFamily: FontFamily = Impact,
    val fontName: String = "Stroke",
    val fontWeight: FontWeight = FontWeight.Normal,
    val text: String = "GOOD",
    val bordered: Boolean = true
)

val fonts = listOf(
    CustomFont(
        fontFamily = Impact,
        fontName = "Impact",
        fontWeight = FontWeight.Normal,
        text = "Good",
        bordered = false,
    ),
    CustomFont(
        fontFamily = Roboto_Thin,
        fontName = "Roboto",
        fontWeight = FontWeight.Normal,
        text = "Good",
        bordered = false,
    ),
    CustomFont(
        fontFamily = Roboto_Bold,
        fontName = "Shadowed",
        fontWeight = FontWeight.Normal,
        text = "GOOD",
        bordered = false,
    ),
    CustomFont(
        fontFamily = Impact,
        fontName = "Stroke",
        fontWeight = FontWeight.Normal,
        text = "GOOD",
        bordered = true
    )
)

@Composable
fun SelectFontComponent(
    customFont: CustomFont = CustomFont(),
    onCustomFontClicked: (CustomFont) -> Unit = {},
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                top = 12.dp,
                start = 16.dp,
                end = 16.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(fonts) { font ->
            SelectFontItem(
                modifier = Modifier
                    .clickable { onCustomFontClicked(font) },
                customFont = font,
                selected = customFont == font,
            )
        }
    }
}

@Preview
@Composable
private fun SelectFontComponentPreview() {
    SelectFontComponent()
}

@Composable
fun SelectFontItem(
    modifier: Modifier = Modifier,
    customFont: CustomFont,
    selected: Boolean = false
) {
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(if (selected) Color(android.graphics.Color.parseColor("#2B2930")) else Color.Transparent)
            .padding(all = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(customFont.bordered) {
            BorderedText(
                text = customFont.text,
                fontSize = 28.sp.value,
                fontFamily = customFont.fontFamily,
                fontWeight = customFont.fontWeight,
            )
        } else {
            Box {
                Text(
                    text = customFont.text,
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = customFont.fontFamily,
                        fontSize = 28f.sp,
                        fontWeight = customFont.fontWeight
                    )
                )
            }
        }

        Text(
            text = customFont.fontName,
            style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight(300),
                fontFamily = Manrope,
                color = Color.White,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectFontItemPreview() {
    SelectFontItem(
        customFont = fonts[1],
        selected = true
    )
}