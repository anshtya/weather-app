package com.anshtya.weatherapp.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat
import com.anshtya.weatherapp.presentation.navigation.WeatherNavigation

@Composable
fun WeatherApp() {

    val context = LocalContext.current
    var notificationPermissionGranted by rememberSaveable {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else mutableStateOf(true)
    }

    if (!notificationPermissionGranted) {
        val requestPermissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                notificationPermissionGranted = isGranted
            }
        )
//        AlertDialog(
//            onDismissRequest = {},
//            confirmButton = {
//                Button(
//                    onClick = {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//                        }
//                    }
//                ) {
//                    Text("Allow Permission")
//                }
//            },
//            text = {
//                Text(
//                    text = "Notification permission is needed to get periodic weather updates",
//                    textAlign = TextAlign.Center
//                )
//            }
//        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Notification permission is needed to get periodic weather updates",
                textAlign = TextAlign.Center
            )
            Button(
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            ) {
                Text("Allow Permission")
            }
        }
    } else {
        WeatherNavigation()
    }
}