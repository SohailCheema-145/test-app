package com.example.testapp.usecases

import com.example.testapp.api.repositories.DrugRepository
import javax.inject.Inject

open class FetchDrugsUseCase @Inject constructor(
    private val drugRepository: DrugRepository
) {
    suspend operator fun invoke() = drugRepository.fetchMedicines()
}
