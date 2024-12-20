package com.icdominguez.icdominguez.master_meme.domain.usecases

import com.icdominguez.icdominguez.master_meme.domain.repository.MemesRepository
import javax.inject.Inject

class DeleteMemeUseCase @Inject constructor(
    private val memesRepository: MemesRepository
) {
    suspend operator fun invoke(memeId: Int) {
        return memesRepository.delete(memeId)
    }
}