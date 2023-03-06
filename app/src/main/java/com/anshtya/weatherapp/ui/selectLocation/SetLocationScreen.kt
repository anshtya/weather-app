package com.anshtya.weatherapp.ui.selectLocation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anshtya.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun SelectLocationScreen(modifier: Modifier = Modifier) {
    Column(modifier.padding(horizontal = 10.dp)) {
        Spacer(Modifier.height(10.dp))
        SearchBar()
        Spacer(Modifier.height(10.dp))
        LocationList()
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    TextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        placeholder = {
            Text(text = "Search")
        },
        shape = RoundedCornerShape(25.dp),
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
fun LocationList(
    modifier: Modifier = Modifier,
    list: List<String> = List(15) { "New Delhi" }
) {
    LazyColumn(modifier) {
        items(items = list) {
            Location(name = it)
        }
    }
}

@Composable
fun Location(name: String) {
    Row(Modifier
        .height(70.dp)
        .fillMaxWidth()
        .clickable(enabled = true) {}
    ) {
        Text(
            text = name
        )
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    WeatherAppTheme {
        SearchBar()
    }
}

@Preview
@Composable
fun SelectLocationScreenPreview() {
    WeatherAppTheme {
        SelectLocationScreen()
    }
}

@Preview
@Composable
fun LoactionPreview() {
    WeatherAppTheme {
        Location("New Delhi")
    }
}