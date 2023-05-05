package com.anshtya.weatherapp.presentation.screens.addLocation

import android.widget.Toast
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.anshtya.weatherapp.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectLocationScreen(
    uiState: SearchLocationUiState,
    onBackClick: () -> Unit,
    onTextChange: (String) -> Unit,
    onSubmit: (String) -> Unit,
    onLocationClick: (String) -> Unit,
    onErrorShown: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            SearchBar(
                searchText = uiState.searchText,
                onBackClick = onBackClick,
                onTextChange = onTextChange,
                onSubmit = onSubmit
            )
        },
        content = { paddingValues ->
            Column(
                modifier = modifier.padding(paddingValues)
            ) {
                LocationList(
                    uiState = uiState,
                    onLocationClick = onLocationClick,
                    onErrorShown = onErrorShown
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchText: String,
    onBackClick: () -> Unit,
    onTextChange: (String) -> Unit,
    onSubmit: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var id by rememberSaveable { mutableStateOf("") }
        LaunchedEffect(searchText) {
            delay(700L)
            if (id != searchText.trimEnd()) {
                id = searchText
                if (id.isNotEmpty()) {
                    onSubmit(id)
                }
            }
        }
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(35.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Spacer(modifier = Modifier.size(10.dp))
        TextField(
            value = searchText,
            onValueChange = { onTextChange(it) },
            colors = TextFieldDefaults.textFieldColors(
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
fun LocationList(
    uiState: SearchLocationUiState,
    onLocationClick: (String) -> Unit,
    onErrorShown: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        if (uiState.searchText.isEmpty()) {
            Text(
                text = stringResource(id = R.string.enter_location),
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            uiState.searchLocations?.let { locations ->
                if (locations.isEmpty() && !uiState.isLoading) {
                    Text(
                        text = stringResource(id = R.string.no_results),
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyColumn {
                        items(items = locations) {
                            LocationItem(
                                locationName = it.name,
                                locationRegionCountry = "${it.region}, ${it.country}",
                                modifier = Modifier.clickable { onLocationClick(it.url) }
                            )
                        }
                    }
                }
            }
            uiState.errorMessage?.let { message ->
                Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
                onErrorShown()
            }
        }
    }
}

//@Composable
//fun AddCurrentLocationButton(modifier: Modifier = Modifier) {
//    Surface(
//        shape = RoundedCornerShape(10.dp),
//        modifier = modifier.fillMaxWidth().clickable { }
//    ) {
//        Text(
//            text = "Add Current Location",
//            modifier = Modifier
//                .padding(horizontal = 5.dp, vertical = 10.dp)
//        )
//    }
//}