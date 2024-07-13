package com.example.testapp.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testapp.db.room.daos.DrugDao
import com.example.testapp.db.room.entities.DrugEntity

@Database(entities = [DrugEntity::class], version = 1, exportSchema = false)
abstract class TestAppDatabase : RoomDatabase() {
    abstract fun drugDao(): DrugDao
}