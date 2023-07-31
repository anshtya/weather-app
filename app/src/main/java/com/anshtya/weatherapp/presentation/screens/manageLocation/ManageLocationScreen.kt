package com.anshtya.weatherapp.presentation.screens.manageLocation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageLocationScreen(
    uiState: ManageLocationUiState,
    onBackClick: () -> Unit,
    selectLocation: (String) -> Unit,
    onAddLocationClick: () -> Unit,
    onLocationClick: (String) -> Unit,
    onDeleteLocation: () -> Unit,
    onErrorShown: () -> Unit,
    modifier: Modifier = Modifier
) {
    var deleteClicked by remember { mutableStateOf(false) }
    BackHandler(deleteClicked) {
        deleteClicked = false
    }
    if(uiState.isItemDeleted) {
        deleteClicked = false
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (deleteClicked) {
                                deleteClicked = false
                            } else {
                                onBackClick()
                            }
                        }
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = onAddLocationClick
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                    IconButton(
                        onClick = {
                            deleteClicked = if (!deleteClicked) {
                                true
                            } else {
                                onDeleteLocation()
                                true
                            }
                        }
                    ) {
                        if (deleteClicked) {
                            Icon(Icons.Default.Check, contentDescription = "Delete")
                        } else {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                },
                title = { Text("Manage Locations") }
            )
        },
    ) {
        Box(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
        ) {
            LazyColumn {
                items(uiState.savedLocations.weatherList) { location ->
                    SavedLocationItem(
                        savedLocation = location,
                        showCelsius = uiState.savedLocations.showCelsius,
                        onCheck = selectLocation,
                        onLongClick = { deleteClicked = true },
                        onLocationClick = onLocationClick,
                        isCheckEnabled = deleteClicked
                    )
                }
            }
            uiState.errorMessage?.let { message ->
                Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
                onErrorShown()
            }
        }
    }
}