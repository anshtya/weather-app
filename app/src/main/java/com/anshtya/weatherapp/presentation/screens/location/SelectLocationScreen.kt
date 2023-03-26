package com.anshtya.weatherapp.presentation.screens.location

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anshtya.weatherapp.R
import com.anshtya.weatherapp.domain.model.SearchLocation
import com.anshtya.weatherapp.presentation.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun SelectLocationScreen(
    modifier: Modifier = Modifier,
    viewModel: LocationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier.padding(horizontal = 10.dp)) {
            Spacer(Modifier.height(10.dp))
            SearchBar(
                searchText = uiState.searchText,
                onTextChange = { text -> viewModel.onSearchTextChange(text) },
                onSubmit = { text -> viewModel.onSubmitSearch(text) }
            )
            Spacer(Modifier.height(10.dp))
            LocationList(
                uiState = uiState
            )
        }
    }
}

@Composable
fun SearchBar(
    searchText: String,
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
    onSubmit: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var id by rememberSaveable { mutableStateOf(searchText) }
        LaunchedEffect(
            key1 = searchText
        ) {
            delay(700L)
            if (searchText.isNotEmpty() && id != searchText) {
                id = searchText
                onSubmit(id)
            }
        }
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
                .clip(RoundedCornerShape(20.dp))
                .clickable(enabled = true) {}
        )
        Spacer(modifier = Modifier.size(10.dp))
        TextField(
            value = searchText,
            onValueChange = { onTextChange(it) },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            placeholder = {
                Text(stringResource(id = R.string.search))
            },
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
    uiState: SearchLocationState,
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (uiState.searchText.isEmpty()) {
            Text(
                text = stringResource(id = R.string.enter_location),
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            uiState.searchLocations?.let { locations ->
                if (locations.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.no_results),
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.clip(RoundedCornerShape(20.dp))
                    ) {
                        items(items = locations) {
                            Location(location = it)
                        }
                    }
                }
            }
            uiState.errorMessage?.let { message ->
                Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun Location(
    location: SearchLocation,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(70.dp)
            .fillMaxWidth()
            .clickable(enabled = true) {},
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = location.name,
            style = Typography.headlineSmall
        )
        Text(
            text = "${location.region}, ${location.country}",
            style = Typography.bodyMedium
        )
    }
}