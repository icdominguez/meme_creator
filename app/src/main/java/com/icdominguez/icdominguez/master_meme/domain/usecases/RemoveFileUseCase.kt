package com.icdominguez.icdominguez.master_meme.domain.usecases

import com.icdominguez.icdominguez.master_meme.domain.repository.FileManagerRepository
import javax.inject.Inject

class RemoveFileUseCase @Inject constructor(
    private val fileManagerRepository: FileManagerRepository
) {
    operator fun invoke(path: String) = fileManagerRepository.removeFile(path)
}