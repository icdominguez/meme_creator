package com.icdominguez.icdominguez.memecreator.domain.usecases

import androidx.compose.ui.graphics.layer.GraphicsLayer
import com.icdominguez.icdominguez.memecreator.FileManager
import com.icdominguez.icdominguez.memecreator.domain.model.MemeEntity
import com.icdominguez.icdominguez.memecreator.domain.repository.MemesRepository
import javax.inject.Inject

class InsertNewMemeUseCase @Inject constructor(
    private val memesRepository: MemesRepository,
    private val fileManager: FileManager,
) {
    suspend operator fun invoke(graphicsLayer: GraphicsLayer) {
        val bitmap = graphicsLayer.toImageBitmap()
        val path = fileManager.saveBitmapToFile(bitmap)
        path?.let {
            val memeEntity = MemeEntity(path = path)
            memesRepository.insert(memeEntity)
        }
    }
}