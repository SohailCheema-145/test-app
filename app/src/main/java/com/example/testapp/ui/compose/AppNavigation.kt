package com.example.testapp.ui.compose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testapp.db.room.entities.DrugEntity
import com.example.testapp.ui.compose.views.DrugDetailScreen
import com.example.testapp.ui.compose.views.HomeScreen
import com.example.testapp.ui.compose.views.LoginScreen
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "login") {
        composable(Routes.ROUTE_LOGIN) {
            LoginScreen { username ->
                navController.navigate("home/$username") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
        composable(Routes.ROUTE_HOME) { backStackEntry ->
            HomeScreen(
                navController,
                username = backStackEntry.arguments?.getString("username") ?: ""
            )
        }

        composable(Routes.ROUTE_DRUG_DETAILS) { backStackEntry ->
            val drugJson = backStackEntry.arguments?.getString("drug")
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter(DrugEntity::class.java)
            val drugObject = drugJson?.let { jsonAdapter.fromJson(it) }
            if (drugObject != null) {
                DrugDetailScreen(drug = drugObject)
            }
        }
    }
}
