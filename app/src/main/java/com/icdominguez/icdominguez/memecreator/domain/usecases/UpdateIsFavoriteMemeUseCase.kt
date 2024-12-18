package com.icdominguez.icdominguez.memecreator.domain.usecases

import com.icdominguez.icdominguez.memecreator.domain.repository.MemesRepository
import javax.inject.Inject

class UpdateIsFavoriteMemeUseCase @Inject constructor(
    private val memesRepository: MemesRepository
) {
    suspend operator fun invoke(memeId: Int, isFavorite: Boolean) {
        return memesRepository.updateFavorite(memeId, isFavorite)
    }
}