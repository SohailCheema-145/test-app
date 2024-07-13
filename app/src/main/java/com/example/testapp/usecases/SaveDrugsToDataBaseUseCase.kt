package com.example.testapp.usecases

import com.example.testapp.api.repositories.DrugRepository
import com.example.testapp.db.room.entities.DrugEntity
import javax.inject.Inject

open class SaveDrugsToDataBaseUseCase @Inject constructor(
    private val drugRepository: DrugRepository
) {
    suspend operator fun invoke(drugs: List<DrugEntity>) = drugRepository.saveDrugsToDataBase(drugs)
}
