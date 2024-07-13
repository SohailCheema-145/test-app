package com.example.testapp.ui.compose.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.testapp.R
import com.example.testapp.api.ApiState
import com.example.testapp.db.room.entities.DrugEntity
import com.example.testapp.ui.compose.Routes.ROUTE_DRUG_DETAILS
import com.example.testapp.ui.viewModels.DrugViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@Composable
fun HomeScreen(
    navController: NavHostController,
    username: String,
    drugViewModel: DrugViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = drugViewModel.state.collectAsState().value
    val drugs by drugViewModel.allDrugs.observeAsState()
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val jsonAdapter = moshi.adapter(DrugEntity::class.java)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val greeting = drugViewModel.getGreetingMessage(context)
        Text(color = MaterialTheme.colorScheme.primary, text = "$greeting, $username!")
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.padding(bottom = 8.dp)) {
            items(drugs ?: emptyList()) { drug ->
                DrugCard(drug) { selectedDrug ->
                    //convert object to json string
                    val drugJson = jsonAdapter.toJson(selectedDrug)
                    //navigate to detail screen
                    navController.navigate(
                        ROUTE_DRUG_DETAILS.replace("{drug}", drugJson)
                    )
                }
            }
        }
    }

    when (state) {
        is ApiState.Loading -> {
            //show progress bar
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ApiState.Success -> {
            //no need to do anything here
        }

        is ApiState.Error -> {
            //show api error only if previous data is empty
            if (drugs?.isEmpty() == true) {
                Text(
                    modifier = Modifier.padding(vertical = 60.dp, horizontal = 16.dp),
                    text = state.message,
                    color = Color.Red
                )
            }
        }
    }
}

@Composable
fun DrugCard(drug: DrugEntity, onClick: (drug: DrugEntity) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { /* Navigate to detail screen */
                onClick(drug)
            },
        elevation = CardDefaults.cardElevation()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CardItem(label = stringResource(R.string.name), value = drug.name)
            CardItem(label = stringResource(R.string.dose), value = drug.dose)
            CardItem(label = stringResource(R.string.strength), value = drug.strength)
        }
    }
}

@Composable
fun CardItem(label: String, value: String) {
    Row(Modifier.fillMaxWidth()) {
        Text(text = "$label: ", color = MaterialTheme.colorScheme.secondary)
        Text(text = value, color = MaterialTheme.colorScheme.primary)
    }
}
