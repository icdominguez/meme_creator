package com.icdominguez.icdominguez.master_meme.domain.usecases

import com.icdominguez.icdominguez.master_meme.domain.repository.MemeTemplatesRepository
import javax.inject.Inject

class GetMemeTemplatesUseCase @Inject constructor(
    private val memeTemplatesRepository: MemeTemplatesRepository
) {
    suspend operator fun invoke() : List<String> =
        memeTemplatesRepository.getMemeTemplates().map { it.name }
}