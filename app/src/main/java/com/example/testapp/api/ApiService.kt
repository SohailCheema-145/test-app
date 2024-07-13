package com.example.testapp.api

import com.example.testapp.api.responseModels.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("v3/ebbdfc5e-c067-4a9c-9f48-04ad42bbd486")
    suspend fun getAllMedicines(): Response<ApiResponse>
}
