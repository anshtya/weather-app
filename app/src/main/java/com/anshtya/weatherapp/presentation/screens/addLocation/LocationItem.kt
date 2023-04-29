package com.anshtya.weatherapp.presentation.screens.addLocation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anshtya.weatherapp.domain.model.SearchLocation
import com.anshtya.weatherapp.presentation.ui.theme.Typography

@Composable
fun LocationItem(
    location: SearchLocation,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = location.name,
            style = Typography.titleLarge
        )
        Text(
            text = "${location.region}, ${location.country}",
            style = Typography.bodyMedium
        )
    }
}