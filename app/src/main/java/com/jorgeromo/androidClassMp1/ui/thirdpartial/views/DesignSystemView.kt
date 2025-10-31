package com.jorgeromo.androidClassMp1.ui.thirdpartial.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jorgeromo.androidClassMp1.ui.designsystem.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesignSystemView() {
    var email by remember { mutableStateOf("") }
    var query by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Sistema de diseño") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoCard(
                title = "¿Qué es un sistema de diseño?",
                subtitle = "Es una colección de componentes reutilizables (botones, campos, tarjetas) con reglas visuales y de interacción consistentes."
            )

            PrimaryButton(text = "Guardar cambios", onClick = {})
            SecondaryButton(text = "Cancelar", onClick = {})

            LabeledTextField(
                label = "Correo electrónico",
                value = email,
                onValueChange = { email = it },
                placeholder = "nombre@correo.com"
            )

            SearchBarField(
                value = query,
                onValueChange = { query = it }
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoTag(text = "Activo")
                InfoTag(text = "Pendiente")
                InfoTag(text = "Completado")
            }

            ProfileAvatar(name = "Jorge Romo")

            LoadingIndicator()
        }
    }
}
