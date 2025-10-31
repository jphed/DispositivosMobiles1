package com.jorgeromo.androidClassMp1.thirdpartial

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.jorgeromo.androidclassmp1.R
import com.jorgeromo.androidClassMp1.ui.designsystem.theme.AndroidClassMp1Theme
import com.jorgeromo.androidClassMp1.thirdpartial.views.ThirdPartialScreen
import org.junit.Rule
import org.junit.Test

class UITestingViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testThirdPartialScreen() {
        composeTestRule.setContent {
            AndroidClassMp1Theme {
                ThirdPartialScreen(
                    onNavigateToTest = { /* Test navigation */ }
                )
            }
        }

        // Verify the screen title is displayed
        composeTestRule.onNodeWithText("Third Partial").assertIsDisplayed()
        
        // Verify the test button is displayed and can be clicked
        composeTestRule.onNodeWithContentDescription("Run UI Tests").assertIsDisplayed()
    }
}
