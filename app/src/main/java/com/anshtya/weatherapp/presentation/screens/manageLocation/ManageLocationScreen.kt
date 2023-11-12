package com.anshtya.weatherapp.presentation.screens.manageLocation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anshtya.weatherapp.R
import com.anshtya.weatherapp.domain.model.SavedLocation
import com.anshtya.weatherapp.presentation.components.ManageLocationTopAppBar
import com.anshtya.weatherapp.presentation.ui.theme.Typography
import kotlin.math.roundToInt

@Composable
fun ManageLocationScreen(
    uiState: ManageLocationUiState,
    selectedLocations: Set<String>,
    onBackClick: () -> Unit,
    onLocationCheck: (String) -> Unit,
    onAddLocationClick: () -> Unit,
    onLocationClick: (String) -> Unit,
    onDeleteLocation: () -> Unit,
    onErrorShown: () -> Unit,
    modifier: Modifier = Modifier
) {
    var deleteClicked by remember(uiState) {
        mutableStateOf(false)
    }

    BackHandler(deleteClicked) {
        deleteClicked = false
    }

    Scaffold(
        topBar = {
            ManageLocationTopAppBar(
                deleteClicked = deleteClicked,
                onDeleteClick = { deleteClicked = !deleteClicked },
                onBackClick = onBackClick,
                onAddLocationClick = onAddLocationClick,
                onDeleteLocation = onDeleteLocation
            )
        },
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
        ) {
            LazyColumn {
                items(
                    items = uiState.locationWithPreferences.savedLocations,
                    key = { savedLocation ->
                        savedLocation.id
                    }
                ) { location ->
                    SavedLocationItem(
                        savedLocation = location,
                        showCelsius = uiState.locationWithPreferences.showCelsius,
                        isChecked = selectedLocations.contains(location.id),
                        onLocationCheck = onLocationCheck,
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SavedLocationItem(
    savedLocation: SavedLocation,
    showCelsius: Boolean,
    isChecked: Boolean,
    isCheckEnabled: Boolean,
    onLocationCheck: (String) -> Unit,
    onLongClick: () -> Unit,
    onLocationClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .combinedClickable(
                onClick = {
                    if (isCheckEnabled) {
                        onLocationCheck(savedLocation.id)
                    } else {
                        onLocationClick(savedLocation.id)
                    }
                },
                onLongClick = {
                    onLongClick()
                    onLocationCheck(savedLocation.id)
                }
            )
    ) {
        Row(Modifier.weight(1f)) {
            if (isCheckEnabled) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        onLocationCheck(savedLocation.id)
                    }
                )
            }
            Column {
                Text(
                    savedLocation.name,
                    style = Typography.titleMedium,
                    fontSize = 18.sp
                )
                Text(
                    "${savedLocation.region}, ${savedLocation.country}",
                    style = Typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
        Column {
            Row {
                Image(
                    painter = if (savedLocation.isDay == 1) {
                        painterResource(savedLocation.weatherType.dayIconRes)
                    } else {
                        painterResource(savedLocation.weatherType.nightIconRes)
                    },
                    contentDescription = null
                )
                Text(
                    text = if (showCelsius) {
                        stringResource(
                            R.string.temperature, savedLocation.tempC.roundToInt()
                        )
                    } else {
                        stringResource(
                            R.string.temperature, savedLocation.tempF.roundToInt()
                        )
                    },
                    style = Typography.titleMedium,
                    fontSize = 18.sp
                )
            }
            Text(
                text = if (showCelsius) {
                    stringResource(
                        R.string.max_min_temp,
                        savedLocation.maxTempC.roundToInt(),
                        savedLocation.minTempC.roundToInt()
                    )
                } else {
                    stringResource(
                        R.string.max_min_temp,
                        savedLocation.maxTempF.roundToInt(),
                        savedLocation.minTempF.roundToInt()
                    )
                },
                style = Typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}