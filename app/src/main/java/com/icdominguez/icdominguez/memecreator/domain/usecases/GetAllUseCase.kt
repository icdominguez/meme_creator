package com.icdominguez.icdominguez.memecreator.domain.usecases

import com.icdominguez.icdominguez.memecreator.domain.model.MemeEntity
import com.icdominguez.icdominguez.memecreator.domain.repository.MemesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUseCase @Inject constructor(
    private val memesRepository: MemesRepository
) {
    operator fun invoke(): Flow<List<MemeEntity>> { return memesRepository.getAll() }
}