package com.example.testapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.testapp.db.room.TestAppDatabase
import com.example.testapp.db.room.daos.DrugDao
import com.example.testapp.db.room.entities.DrugEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DrugDaoTests {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: TestAppDatabase
    private lateinit var drugDao: DrugDao

    @Before
    fun setup() {
        // Use an in-memory database for testing
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TestAppDatabase::class.java
        ).allowMainThreadQueries().build()

        drugDao = database.drugDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertDrugEntity() = runBlocking {
        // Create a sample drug entity
        val drugEntity = DrugEntity(1, "Drug A", "10mg", "Dose A")

        // Insert drug entity into database
        drugDao.insert(drugEntity)

        // Retrieve the drug from database
        val retrievedDrug = drugDao.getDrugById(1)

        // Verify that the retrieved drug matches the inserted drug
        assertEquals(drugEntity, retrievedDrug)
    }

    @Test
    fun getAllDrugs() = runBlocking {
        // Insert some sample drugs into the database
        val drug1 = DrugEntity(1, "Drug A", "10mg", "Dose A")
        val drug2 = DrugEntity(2, "Drug B", "20mg", "Dose B")
        drugDao.insert(drug1)
        drugDao.insert(drug2)

        // Retrieve all drugs from the database
        val drugs = drugDao.getAllDrugs()

        // Verify that the retrieved list contains all inserted drugs
        assertEquals(2, drugs.size)
        assertEquals(drug1, drugs[0])
        assertEquals(drug2, drugs[1])
    }

    @Test
    fun deleteAllDrugs() = runBlocking {
        // Insert some sample drugs into the database
        val drug1 = DrugEntity(1, "Drug A", "10mg", "Dose A")
        val drug2 = DrugEntity(2, "Drug B", "20mg", "Dose B")
        drugDao.insert(drug1)
        drugDao.insert(drug2)

        // Delete all drugs from the database
        drugDao.deleteAll()

        // Verify that the database is empty
        val drugs = drugDao.getAllDrugs()
        assert(drugs.isEmpty())
    }
}
