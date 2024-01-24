package com.example.tmtgdemo

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.tmtgdemo.ui.theme.TMTGDemoTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
@HiltAndroidTest
class MainTests {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltTestRule.inject()
        composeTestRule.activity.setContent {
            TMTGDemoTheme {
                SarasotaHomesFinderCompose()
            }
        }
    }
    @Test
    fun navigate_from_home_to_listofhomes() {


        composeTestRule.apply {
            waitUntil {
                composeTestRule
                    .onAllNodesWithTag("HomeButtonTestTag")
                    .fetchSemanticsNodes().size == 1
            }

            onNodeWithTag("HomeButtonTestTag").performClick()

            waitUntil {
                composeTestRule
                    .onAllNodesWithTag("ReturnHomeTestTag")
                    .fetchSemanticsNodes().size == 1
            }
        }
    }

    
}