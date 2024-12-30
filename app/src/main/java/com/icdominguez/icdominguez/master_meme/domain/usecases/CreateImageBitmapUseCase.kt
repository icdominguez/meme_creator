package com.icdominguez.icdominguez.master_meme.domain.usecases

import com.icdominguez.icdominguez.master_meme.domain.repository.FileManagerRepository
import java.io.File
import javax.inject.Inject

class CreateImageBitmapUseCase @Inject constructor(
    private val fileManagerRepository: FileManagerRepository
) {
    operator fun invoke(file: File) = fileManagerRepository.createImageBitmap(file)
}
