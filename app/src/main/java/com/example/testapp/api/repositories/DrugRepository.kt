package com.example.testapp.api.repositories

import androidx.lifecycle.LiveData
import com.example.testapp.api.ApiService
import com.example.testapp.db.room.daos.DrugDao
import com.example.testapp.db.room.entities.DrugEntity
import com.example.testapp.api.responseModels.ApiResponse
import javax.inject.Inject

open class DrugRepository @Inject constructor(
    private val apiService: ApiService,
    private val drugDao: DrugDao
) {
    val allDrugs: LiveData<List<DrugEntity>> = drugDao.getAll()

    suspend fun fetchMedicines(): ApiResponse? {
        val response = apiService.getAllMedicines()
        if (response.isSuccessful) {
            response.body()?.let {
                processResponseAndSaveDrugs(it)
                return it
            }
        }
        return null
    }

    private suspend fun processResponseAndSaveDrugs(apiResponse: ApiResponse) {
        val drugs =
            apiResponse.problems.flatMap { it.Diabetes + if (it.Asthma?.isNotEmpty() == true) it.Asthma else emptyList() }
                .flatMap { it.medications }
                .flatMap { it.medicationsClasses }
                .flatMap { it.className + it.className2 }
                .flatMap { it.associatedDrug + if (!it.associatedDrug2.isNullOrEmpty()) it.associatedDrug2 else emptyList() }
        if (drugs.isNotEmpty()) {
            drugDao.deleteAll()
            drugDao.insertAll(drugs)
        }
    }

    suspend fun deleteAllDrugs() {
        drugDao.deleteAll()
    }

    suspend fun saveDrugsToDataBase(drugs: List<DrugEntity>) {
        drugDao.insertAll(drugs)
    }
}
