package com.icdominguez.icdominguez.master_meme.presentation.model

import androidx.compose.ui.graphics.ImageBitmap
import java.util.Calendar

data class Meme(
    val id: Int,
    val path: String,
    val imageBitmap: ImageBitmap? = null,
    var selected: Boolean = false,
    var isFavorite: Boolean = false,
    var dateCreated: Calendar
)