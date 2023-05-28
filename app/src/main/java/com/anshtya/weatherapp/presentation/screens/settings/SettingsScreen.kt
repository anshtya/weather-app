package com.anshtya.weatherapp.presentation.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anshtya.weatherapp.presentation.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onBackClick: () -> Unit,
    onUnitSelect: (Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { onBackClick() }
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                title = { Text("Settings") }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                SettingsItem(
                    mainText = "Unit", subText = if (uiState.showCelsius) "°C" else "°F",
                    optionsList = listOf("°C", "°F"),
                    onOptionClick = {
                        if (it == "°C") {
                            onUnitSelect(true)
                        } else {
                            onUnitSelect(false)
                        }
                    }
                )
            }
        }
    )
}

@Composable
fun SettingsItem(
    mainText: String,
    subText: String,
    optionsList: List<String>,
    onOptionClick: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { expanded = true })
            .padding(horizontal = 15.dp, vertical = 10.dp)
    ) {
        Text(mainText, style = Typography.titleMedium, fontSize = 18.sp)
        Text(subText, style = Typography.bodyMedium, color = Color.Gray)
        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false }
        ) {
            optionsList.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        expanded = false
                        onOptionClick(it)
                    }
                )
            }
        }
    }
    Divider(thickness = Dp.Hairline)
}