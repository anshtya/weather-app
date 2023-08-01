package com.anshtya.weatherapp.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageLocationTopAppBar(
    deleteClicked: Boolean,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
    onAddLocationClick: () -> Unit,
    onDeleteLocation: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = {
                    if (deleteClicked) {
                        onDeleteClick()
                    } else {
                        onBackClick()
                    }
                }
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        },
        actions = {
            IconButton(
                onClick = onAddLocationClick
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(R.string.add)
                )
            }
            IconButton(
                onClick = {
                    if (deleteClicked) {
                        onDeleteLocation()
                    } else {
                        onDeleteClick()
                    }
                }
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete)
                )
            }
        },
        title = {
            Text(stringResource(R.string.manage_locations))
        }
    )
}