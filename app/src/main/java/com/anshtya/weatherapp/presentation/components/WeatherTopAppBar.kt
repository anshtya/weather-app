package com.anshtya.weatherapp.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.anshtya.weatherapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherTopAppBar(
    title: String,
    onMenuClick: () -> Unit,
    onUpdateClick: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = onMenuClick
            ) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = stringResource(R.string.show_navigation_menu)
                )
            }
        },
        actions = {
            IconButton(
                onClick = onUpdateClick
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Refresh Weather")
            }
        },
        title = { Text(title) }
    )
}