package com.icdominguez.icdominguez.memecreator.newmeme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import com.icdominguez.icdominguez.memecreator.ui.theme.Impact

data class TextMeme(
    val id: Int = 0,
    var text: String = "TAP TWICE TO EDIT",
    val typography: FontFamily = Impact,
    val color: Color = Color.White,
    var fontSize: Float = 20f,
    var offset: Offset? = null,
)