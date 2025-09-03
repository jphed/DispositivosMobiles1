package com.jorgeromo.androidClassMp1.firstpartial.onboarding.views

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private const val TAG = "BottomBarView"

@Composable
fun BottomBarView(
    isLastPage: Boolean,
    page: Int,
    total: Int,
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
    Log.v(TAG, "Renderizando barra inferior - Página: $page/$total, Última página: $isLastPage")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextButton(
            enabled = page > 0,
            onClick = {
                Log.d(TAG, "Botón Anterior clickeado")
                onPrev()
            }
        ) {
            Text("Anterior")
        }

        Spacer(Modifier.weight(1f))

        Button(onClick = {
            Log.d(TAG, "Botón ${if (isLastPage) "Empezar" else "Siguiente"} clickeado")
            onNext()
        }) {
            Text(if (isLastPage) "Empezar" else "Siguiente")
        }
    }
}