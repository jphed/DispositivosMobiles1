package com.jorgeromo.androidClassMp1.thirdpartial.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController

private const val TEST_TAG_COUNTER = "ui_counter_text"
private const val TEST_TAG_BUTTON = "ui_counter_button"

@Composable
fun UITestingScreen(
    onRunTests: () -> Unit = {},
    modifier: Modifier = Modifier,
    navController: NavController? = null
) {
    var count by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = "Counter: $count",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.semantics { 
                contentDescription = TEST_TAG_COUNTER 
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { 
                count++
                onRunTests()
            },
            modifier = Modifier.semantics { 
                contentDescription = TEST_TAG_BUTTON 
            }
        ) {
            Text("Increment")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UITestingScreenPreview() {
    MaterialTheme {
        Surface {
            UITestingScreen()
        }
    }
}
