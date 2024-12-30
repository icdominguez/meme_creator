package com.icdominguez.icdominguez.master_meme.domain.repository

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import java.io.File

interface FileManagerRepository {
    suspend fun saveImageToFile(imageBitmap: ImageBitmap, inCache: Boolean = false): String?
    fun saveImageToCache(imageBitmap: ImageBitmap): Uri?
    fun createImageBitmap(file: File): ImageBitmap?
    fun removeFile(path: String)
    fun cleanCache()
}