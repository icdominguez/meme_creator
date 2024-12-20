package com.icdominguez.icdominguez.master_meme.presentation.model

import androidx.compose.ui.geometry.Offset
import com.icdominguez.icdominguez.master_meme.presentation.screens.newmeme.composables.CustomFont

data class TextMeme(
    val id: Int = 0,
    var text: String = "TAP TWICE TO EDIT",
    val typography: CustomFont = CustomFont(),
    val color: String = "#FFFFFF",
    var fontSize: Float = 20f,
    var offset: Offset? = null,
)