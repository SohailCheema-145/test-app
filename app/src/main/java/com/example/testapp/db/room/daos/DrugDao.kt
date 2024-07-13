package com.example.testapp.db.room.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testapp.db.room.entities.DrugEntity

@Dao
interface DrugDao {
    @Query("SELECT * FROM drug_entity")
    suspend fun getAllDrugs(): List<DrugEntity>

    @Query("SELECT * FROM drug_entity")
    fun getAll(): LiveData<List<DrugEntity>>

    @Query("SELECT * FROM drug_entity WHERE id = :id")
    suspend fun getDrugById(id: Long): DrugEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(drug: DrugEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(drugs: List<DrugEntity>)

    @Query("DELETE FROM drug_entity")
    suspend fun deleteAll()
}