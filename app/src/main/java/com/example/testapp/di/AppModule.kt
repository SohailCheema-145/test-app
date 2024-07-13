package com.example.testapp.di

import android.content.Context
import androidx.room.Room
import com.example.testapp.api.ApiService
import com.example.testapp.api.repositories.DrugRepository
import com.example.testapp.db.room.TestAppDatabase
import com.example.testapp.db.room.daos.DrugDao
import com.example.testapp.usecases.DeleteAllDrugsUseCase
import com.example.testapp.usecases.FetchDrugsUseCase
import com.example.testapp.usecases.SaveDrugsToDataBaseUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): TestAppDatabase {
        return Room.databaseBuilder(
            appContext,
            TestAppDatabase::class.java,
            "test_app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDrugDao(database: TestAppDatabase): DrugDao {
        return database.drugDao()
    }

    @Provides
    @Singleton
    fun provideDrugRepository(
        apiService: ApiService,
        drugDao: DrugDao
    ): DrugRepository {
        return DrugRepository(apiService, drugDao)
    }

    @Provides
    @Singleton
    fun provideFetchMedicinesUseCase(
        drugRepository: DrugRepository
    ): FetchDrugsUseCase {
        return FetchDrugsUseCase(drugRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteAllDrugsUseCase(
        drugRepository: DrugRepository
    ): DeleteAllDrugsUseCase {
        return DeleteAllDrugsUseCase(drugRepository)
    }

    @Provides
    @Singleton
    fun provideSaveDrugsToDataBaseUseCase(
        drugRepository: DrugRepository
    ): SaveDrugsToDataBaseUseCase {
        return SaveDrugsToDataBaseUseCase(drugRepository)
    }
}
