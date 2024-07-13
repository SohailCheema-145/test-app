package com.example.testapp.db.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "drug_entity")
data class DrugEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val dose: String,
    val strength: String
)