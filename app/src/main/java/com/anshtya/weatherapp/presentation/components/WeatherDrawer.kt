package com.anshtya.weatherapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.anshtya.weatherapp.R
import com.anshtya.weatherapp.domain.model.Weather

@Composable
fun WeatherDrawer(
    weatherLocations: List<Weather>,
    drawerState: DrawerState,
    onDrawerClose: () -> Unit,
    onChangeSelectedId: (String) -> Unit,
    onSettingsClick: () -> Unit,
    onManageLocationsClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet(modifier.requiredWidth(250.dp)) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
                ) {
                    DrawerHeader(
                        onSettingsClick = onSettingsClick
                    )

                    DrawerWeatherLocations(
                        weatherLocations = weatherLocations,
                        onClick = {
                            onChangeSelectedId(it.weatherLocation.id)
                            onDrawerClose()
                        }
                    )

                    Button(
                        onClick = onManageLocationsClick,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.manage_locations))
                    }
                }
            }
        },
        content = content
    )
}

@Composable
fun DrawerHeader(
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxWidth()) {
        IconButton(
            onClick = onSettingsClick,
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = stringResource(R.string.settings)
            )
        }
    }
}

@Composable
fun DrawerWeatherLocations(
    weatherLocations: List<Weather>,
    onClick: (Weather) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        items(
            items = weatherLocations,
            key = { weather ->
                weather.weatherLocation.id
            }
        ) {
            NavigationDrawerItem(
                label = {
                    Text(it.weatherLocation.name)
                },
                selected = false,
                onClick = { onClick(it) }
            )
        }
    }
}