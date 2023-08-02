package com.anshtya.weatherapp.presentation.screens.addLocation

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.IntentSender
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anshtya.weatherapp.R
import com.anshtya.weatherapp.domain.model.SearchLocation
import com.anshtya.weatherapp.presentation.ui.theme.Typography
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task

@Composable
fun AddLocationScreen(
    uiState: AddLocationUiState,
    onBackClick: () -> Unit,
    onSearchTextChange: (String) -> Unit,
    onLocationClick: (String) -> Unit,
    onAddCurrentLocationClick: () -> Unit,
    onErrorShown: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            SearchBar(
                searchText = uiState.searchText,
                onBackClick = onBackClick,
                onSearchTextChange = onSearchTextChange,
            )
        },
        content = { paddingValues ->
            Column(
                modifier = modifier.padding(paddingValues)
            ) {
                LocationScreenContent(
                    searchLocations = uiState.searchLocations,
                    searchText = uiState.searchText,
                    isLoading = uiState.isLoading,
                    isSearching = uiState.isSearching,
                    onLocationClick = onLocationClick,
                    onAddCurrentLocationClick = onAddCurrentLocationClick
                )
                uiState.errorMessage?.let { message ->
                    Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
                    onErrorShown()
                }
            }
        }
    )
}

@Composable
fun SearchBar(
    searchText: String,
    onBackClick: () -> Unit,
    onSearchTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(35.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Spacer(modifier = Modifier.size(10.dp))
        TextField(
            value = searchText,
            onValueChange = { onSearchTextChange(it) },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            placeholder = { Text(stringResource(id = R.string.search)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
        )
    }
}

@Composable
fun LocationScreenContent(
    searchLocations: List<SearchLocation>,
    searchText: String,
    isLoading: Boolean,
    isSearching: Boolean,
    onLocationClick: (String) -> Unit,
    onAddCurrentLocationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)
            || permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)
        ) {
            onAddCurrentLocationClick()
        }
    }

    val settingResultRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        if (activityResult.resultCode == RESULT_OK) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    Box(modifier.fillMaxSize()) {
        if (isLoading || searchText.isNotEmpty() && isSearching) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (searchText.isEmpty()) {
            AddCurrentLocationButton(
                onClick = {
                    //call gps
                    getLocationSetting(
                        context,
                        onEnabled = {
                            permissionLauncher.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        },
                        onDisabled = { intentSenderRequest ->
                            settingResultRequest.launch(intentSenderRequest)
                        }
                    )

                }
            )
            Text(
                text = stringResource(id = R.string.enter_location),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (!isSearching) {
            if (searchLocations.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.no_results),
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn {
                    items(items = searchLocations) {
                        LocationItem(
                            locationName = it.name,
                            locationRegionCountry = "${it.region}, ${it.country}",
                            modifier = Modifier
                                .clickable {
                                    focusManager.clearFocus()
                                    onLocationClick(it.url)
                                }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddCurrentLocationButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Text(
            text = stringResource(R.string.add_current_location),
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 10.dp)
        )
    }
}

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

fun getLocationSetting(
    context: Context,
    onEnabled: () -> Unit,
    onDisabled: (IntentSenderRequest) -> Unit
) {
    val locationRequest = LocationRequest
        .Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, 0)
        .build()
    val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
    val client: SettingsClient = LocationServices.getSettingsClient(context)
    val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
    task.apply {
        addOnSuccessListener { onEnabled() }
        addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {
                    val intentSenderRequest = IntentSenderRequest
                        .Builder(exception.resolution)
                        .build()
                    onDisabled(intentSenderRequest)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }
}