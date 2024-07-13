package com.example.testapp.ui.compose.views


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testapp.R
import com.example.testapp.db.room.entities.DrugEntity


@Composable
fun DrugDetailScreen(drug: DrugEntity) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = 18.sp,
            text = stringResource(R.string.details),
            color = MaterialTheme.colorScheme.primary
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            elevation = CardDefaults.cardElevation()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                CardItem(label = stringResource(R.string.name), value = drug.name)
                CardItem(label = stringResource(R.string.dose), value = drug.dose)
                CardItem(label = stringResource(R.string.strength), value = drug.strength)
            }
        }
    }
}