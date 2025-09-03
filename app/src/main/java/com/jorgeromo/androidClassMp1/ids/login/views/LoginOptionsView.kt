package com.jorgeromo.androidClassMp1.ids.login.views

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jorgeromo.androidClassMp1.R
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

@Composable
fun LoginOptionsView(navController: NavHostController? = null) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var isFacialLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Función para validar contraseña
    fun isValidPassword(password: String): Boolean {
        if (password.length < 6) return false
        val hasUpperCase = password.any { it.isUpperCase() }
        if (!hasUpperCase) return false
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }
        if (!hasSpecialChar) return false
        return true
    }

    fun getPasswordErrorMessage(password: String): String {
        return when {
            password.length < 6 -> "Mínimo 6 caracteres"
            !password.any { it.isUpperCase() } -> "Requiere una mayúscula"
            !password.any { !it.isLetterOrDigit() } -> "Requiere un carácter especial"
            else -> ""
        }
    }

    // Función para validar y iniciar sesión
    fun validateAndLogin() {
        passwordError = ""

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Ingrese un email válido", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidPassword(password)) {
            passwordError = getPasswordErrorMessage(password)
            return
        }

        isLoading = true
        Toast.makeText(context, "Iniciando sesión...", Toast.LENGTH_SHORT).show()

        android.os.Handler().postDelayed({
            isLoading = false
            Toast.makeText(context, "¡Login exitoso!", Toast.LENGTH_SHORT).show()
            navController?.navigate("FirstPartialRoute") {
                popUpTo(0)
            }
        }, 2000)
    }

    // Función para verificar disponibilidad de biometricos (versión simplificada)
    fun isBiometricAvailable(context: Context): Boolean {
        return try {
            val packageManager = context.packageManager
            packageManager.hasSystemFeature(android.content.pm.PackageManager.FEATURE_FINGERPRINT) ||
                    packageManager.hasSystemFeature(android.content.pm.PackageManager.FEATURE_FACE)
        } catch (e: Exception) {
            false
        }
    }

    // Función para iniciar reconocimiento biométrico (versión simplificada)
    fun startBiometricAuthentication() {
        if (!isBiometricAvailable(context)) {
            Toast.makeText(context, "Autenticación biométrica no disponible en este dispositivo", Toast.LENGTH_LONG).show()
            return
        }

        isFacialLoading = true
        Toast.makeText(context, "Iniciando autenticación biométrica...", Toast.LENGTH_SHORT).show()

        // Simulación de autenticación biométrica
        android.os.Handler().postDelayed({
            isFacialLoading = false

            // Simular éxito (75% de probabilidad) o fallo (25% de probabilidad)
            val success = Math.random() < 0.75

            if (success) {
                Toast.makeText(context, "✅ Autenticación biométrica exitosa", Toast.LENGTH_SHORT).show()
                navController?.navigate("FirstPartialRoute") {
                    popUpTo(0)
                }
            } else {
                Toast.makeText(context, "❌ Autenticación fallida. Intente nuevamente.", Toast.LENGTH_SHORT).show()
            }
        }, 3000)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ulsalogo),
            contentDescription = "Logo ULSA",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Inicio de Sesión",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            isError = email.isNotEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches(),
            supportingText = {
                if (email.isNotEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Text("Email inválido", color = MaterialTheme.colorScheme.error)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                if (passwordError.isNotEmpty()) passwordError = ""
            },
            label = { Text("Contraseña") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = icon, contentDescription = "Toggle visibility")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = passwordError.isNotEmpty(),
            supportingText = {
                if (passwordError.isNotEmpty()) {
                    Text(passwordError, color = MaterialTheme.colorScheme.error)
                } else if (password.isNotEmpty()) {
                    Text("6+ chars, 1 mayúscula, 1 especial")
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { validateAndLogin() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading && !isFacialLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Iniciando...")
            } else {
                Text("Iniciar Sesión")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(modifier = Modifier.weight(1f))
            Text(
                text = "O",
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Divider(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // BOTÓN DE RECONOCIMIENTO BIOMÉTRICO
        Button(
            onClick = { startBiometricAuthentication() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            enabled = !isLoading && !isFacialLoading && isBiometricAvailable(context)
        ) {
            if (isFacialLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onSecondary,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Autenticando...")
            } else {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "Autenticación Biométrica",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Autenticación Biométrica")
            }
        }

        // Información sobre el estado biométrico
        Spacer(modifier = Modifier.height(16.dp))

        if (!isBiometricAvailable(context)) {
            Text(
                text = "⚠️ Autenticación biométrica no disponible",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Este dispositivo no soporta huella digital o reconocimiento facial",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        } else {
            Text(
                text = "✅ Autenticación biométrica disponible",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Toque el botón para autenticarse biométricamente",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}