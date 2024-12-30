package com.icdominguez.icdominguez.master_meme.domain.usecases

import androidx.compose.ui.graphics.ImageBitmap
import com.icdominguez.icdominguez.master_meme.domain.repository.FileManagerRepository
import javax.inject.Inject

class SaveImageToCache @Inject constructor(
    private val fileManagerRepository: FileManagerRepository
) {
    operator fun invoke(imageBitmap: ImageBitmap) = fileManagerRepository.saveImageToCache(imageBitmap)
}