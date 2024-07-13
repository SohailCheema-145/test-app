package com.example.testapp.api

import com.example.testapp.api.responseModels.ApiResponse

sealed class ApiState {
    data object Loading : ApiState()
    data class Success(val data: ApiResponse) : ApiState()
    data class Error(val message: String) : ApiState()
}
