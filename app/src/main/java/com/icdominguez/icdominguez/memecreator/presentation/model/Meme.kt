package com.icdominguez.icdominguez.memecreator.presentation.model

import androidx.compose.ui.graphics.ImageBitmap

data class Meme(
    val id: Int,
    val path: String,
    val imageBitmap: ImageBitmap? = null,
    var selected: Boolean = false,
    var isFavorite: Boolean = false,
)