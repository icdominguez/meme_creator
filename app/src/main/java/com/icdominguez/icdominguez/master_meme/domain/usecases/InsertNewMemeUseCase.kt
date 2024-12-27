package com.icdominguez.icdominguez.master_meme.domain.usecases

import androidx.compose.ui.graphics.layer.GraphicsLayer
import com.icdominguez.icdominguez.master_meme.FileManager
import com.icdominguez.icdominguez.master_meme.domain.model.MemeEntity
import com.icdominguez.icdominguez.master_meme.domain.repository.MemesRepository
import javax.inject.Inject

class InsertNewMemeUseCase @Inject constructor(
    private val memesRepository: MemesRepository,
    private val fileManager: FileManager,
) {
    suspend operator fun invoke(graphicsLayer: GraphicsLayer, fileName: String) {
        val bitmap = graphicsLayer.toImageBitmap()
        val path = fileManager.saveBitmapToFile(bitmap)
        path?.let {
            val memeEntity = MemeEntity(
                name = fileName,
                path = path)
            memesRepository.insert(memeEntity)
        }
    }
}