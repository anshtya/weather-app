package com.anshtya.weatherapp.presentation.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.anshtya.weatherapp.R

@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onBackClick: () -> Unit,
    onShowCelsius: (Boolean) -> Unit
) {
    val listState = rememberScrollState()
    Scaffold(
        topBar = {
            SettingsTopAppBar(
                onBackClick = onBackClick
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(listState)
            ) {
                SettingTitle(text = stringResource(R.string.temperature_unit))
                SettingsChooser(
                    text = stringResource(R.string.celsius),
                    selected = uiState.showCelsius,
                    onClick = { onShowCelsius(true) }
                )
                SettingsChooser(
                    text = stringResource(R.string.fahrenheit),
                    selected = !uiState.showCelsius,
                    onClick = { onShowCelsius(false) }
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopAppBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        title = {
            Text(stringResource(R.string.settings))
        }
    )
}

@Composable
fun SettingTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier.padding(horizontal = 10.dp, vertical = 5.dp)
    )
}

@Composable
fun SettingsChooser(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick
            )
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(text)
        RadioButton(
            selected = selected,
            onClick = onClick
        )
    }
}