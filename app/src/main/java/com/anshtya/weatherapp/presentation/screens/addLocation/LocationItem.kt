package com.anshtya.weatherapp.presentation.screens.addLocation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anshtya.weatherapp.presentation.ui.theme.Typography

@Composable
fun LocationItem(
    locationName: String,
    locationRegionCountry: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(locationName, style = Typography.titleMedium, fontSize = 18.sp)
        Text(locationRegionCountry, style = Typography.bodyMedium, color = Color.Gray)
    }
}