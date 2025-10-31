package com.jorgeromo.androidClassMp1.thirdpartial

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jorgeromo.androidClassMp1.navigation.ScreenNavigation
import com.jorgeromo.androidClassMp1.thirdpartial.ui.UITestingScreen
import com.jorgeromo.androidClassMp1.ui.designsystem.components.PrimaryButton

@Composable
fun ThirdPartialView(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tercer Parcial",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryButton(
            text = "Sistema de diseño",
            onClick = {
                navController.navigate(ScreenNavigation.DesignSystem.route)
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryButton(
            text = "Pruebas de UI",
            onClick = {
                navController.navigate(ScreenNavigation.UITesting.route)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun UITestingView(navController: NavController) {
    UITestingScreen(
        onRunTests = {
            // Aquí podrías lanzar las pruebas programáticamente si fuera necesario
            // Por ahora, solo navegamos a la pantalla de pruebas
        }
    )
}