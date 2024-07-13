package com.example.testapp.ui.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.R
import com.example.testapp.api.ApiState
import com.example.testapp.api.repositories.DrugRepository
import com.example.testapp.db.room.entities.DrugEntity
import com.example.testapp.usecases.DeleteAllDrugsUseCase
import com.example.testapp.usecases.FetchDrugsUseCase
import com.example.testapp.usecases.SaveDrugsToDataBaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
open class DrugViewModel @Inject constructor(
    private val fetchDrugsUseCase: FetchDrugsUseCase,
    drugRepository: DrugRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ApiState>(ApiState.Loading)
    val state: StateFlow<ApiState> = _state

    val allDrugs: LiveData<List<DrugEntity>> = drugRepository.allDrugs

    init {
        fetchMedicines()
    }

    private fun fetchMedicines() {
        viewModelScope.launch {
            try {
                _state.value = ApiState.Loading
                val response = fetchDrugsUseCase.invoke()
                response?.let {
                    _state.value = ApiState.Success(it)
                } ?: run {
                    _state.value = ApiState.Error("No data available")
                }
            } catch (e: Exception) {
                _state.value = ApiState.Error("Exception: ${e.message}")
            }
        }
    }

    fun getGreetingMessage(context: Context): String {
        val rightNow = Calendar.getInstance()
        val currentHour: Int = rightNow.get(Calendar.HOUR_OF_DAY)
        return when (currentHour) {
            in 0..11 -> context.getString(R.string.good_morning)
            in 12..16 -> context.getString(R.string.good_afternoon)
            in 17..19 -> context.getString(R.string.good_evening)
            else -> context.getString(R.string.good_night)
        }
    }
}