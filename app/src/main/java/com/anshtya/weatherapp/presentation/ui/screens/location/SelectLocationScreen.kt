package com.anshtya.weatherapp.presentation.ui.screens.location

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anshtya.weatherapp.R
import com.anshtya.weatherapp.common.Resource
import com.anshtya.weatherapp.domain.model.SearchLocation
import com.anshtya.weatherapp.domain.model.SearchLocationResponse
import com.anshtya.weatherapp.presentation.ui.theme.Typography
import com.anshtya.weatherapp.presentation.ui.theme.WeatherAppTheme

@Composable
fun SelectLocationScreen(
    modifier: Modifier = Modifier,
    viewModel: LocationViewModel = viewModel()
) {
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val locations by viewModel.locations.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()

    Column(modifier.padding(horizontal = 10.dp)) {
        Spacer(Modifier.height(10.dp))
        SearchBar(searchText = searchText) { text ->
            viewModel.onSearchTextChange(text)
        }
        Spacer(Modifier.height(10.dp))
        LocationList(
            location = locations,
            isSearching = isSearching
        )
    }
}

@Composable
fun SearchBar(
    searchText: String,
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit
) {
    Row(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
//        Icon(
//            imageVector = Icons.Default.ArrowBack,
//            contentDescription = null,
//            modifier = Modifier
//                .size(30.dp)
//                .clickable(enabled = true) {}
//        )
//        Spacer(modifier = Modifier.size(10.dp))
        TextField(
            value = searchText,
            onValueChange = {
//                searchText = it
                onTextChange(it)
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            placeholder = {
                Text(stringResource(id = R.string.search))
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
        )
    }
}

@Composable
fun LocationList(
    location: Resource<SearchLocationResponse>,
    isSearching: Boolean,
    modifier: Modifier = Modifier,
) {
    when (location) {
        is Resource.Loading -> {
            Box(modifier = modifier.fillMaxSize()) {
                if (isSearching) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.enter_location),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
        is Resource.Success -> {
            val results = location.data.list
            if (results.isEmpty()) {
                Box(modifier = modifier.fillMaxSize()) {
                    Text(
                        text = stringResource(id = R.string.no_results),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyColumn(
                    modifier = modifier.clip(RoundedCornerShape(20.dp))
                ) {
                    items(items = results) {
                        Location(location = it)
                    }
                }
            }
        }
        is Resource.Error -> {
            Toast.makeText(LocalContext.current, "${location.message}", Toast.LENGTH_LONG).show()
        }
    }
//    if (isSearching) {
//        Box(modifier = modifier.fillMaxSize()) {
//            CircularProgressIndicator(
//                modifier = Modifier.align(Alignment.Center)
//            )
//        }
//    } else {
//        if (locations.isEmpty()) {
//            Box(modifier = modifier.fillMaxSize()) {
//                Text(
//                    text = "Enter a location name",
//                    modifier = Modifier.align(Alignment.Center)
//                )
//            }
//        } else {
//            LazyColumn(
//                modifier = modifier.clip(RoundedCornerShape(20.dp))
//            ) {
//                items(items = locations) {
//                    Location(location = it)
//                }
//            }
//}
//}
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

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SearchBarPreview() {
    WeatherAppTheme {
        SearchBar("hello") {}
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun LoactionPreview() {
    WeatherAppTheme {
        Location(SearchLocation("delhi", "delhi", "delhi", "url"))
    }
}