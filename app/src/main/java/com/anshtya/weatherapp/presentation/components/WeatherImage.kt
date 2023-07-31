package com.anshtya.weatherapp.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun WeatherImage(
    @DrawableRes image: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.size(150.dp)
    ) {
        Image(
            painterResource(image),
            contentDescription = contentDescription,
        )
    }
}