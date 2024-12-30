package com.icdominguez.icdominguez.master_meme.domain.usecases

import androidx.compose.ui.graphics.layer.GraphicsLayer
import com.icdominguez.icdominguez.master_meme.domain.model.MemeEntity
import com.icdominguez.icdominguez.master_meme.domain.repository.FileManagerRepository
import com.icdominguez.icdominguez.master_meme.domain.repository.MemesRepository
import javax.inject.Inject

class InsertNewMemeUseCase @Inject constructor(
    private val memesRepository: MemesRepository,
    private val fileManagerRepository: FileManagerRepository,
) {
    suspend operator fun invoke(graphicsLayer: GraphicsLayer, fileName: String) {
        val bitmap = graphicsLayer.toImageBitmap()
        val path = fileManagerRepository.saveImageToFile(bitmap)
        path?.let {
            val memeEntity = MemeEntity(
                name = fileName,
                path = path)
            memesRepository.insert(memeEntity)
        }
    }
}