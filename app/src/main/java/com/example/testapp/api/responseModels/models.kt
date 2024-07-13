package com.example.testapp.api.responseModels

import com.example.testapp.db.room.entities.DrugEntity

data class ApiResponse(
    val problems: List<Problem>
)

data class Problem(
    val Diabetes: List<Diabetes>,
    val Asthma: List<Diabetes>? = null,
)

data class Diabetes(
    val medications: List<Medication>,
    val labs: List<Lab>
)

data class Medication(
    val medicationsClasses: List<MedicationClass>
)

data class MedicationClass(
    val className: List<DrugClass>,
    val className2: List<DrugClass>
)

data class DrugClass(
    val associatedDrug: List<DrugEntity>,
    val associatedDrug2: List<DrugEntity>? = null
)

data class Lab(
    val missing_field: String
)
