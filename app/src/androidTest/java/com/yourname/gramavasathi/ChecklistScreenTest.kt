package com.yourname.gramavasathi

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.yourname.gramavasathi.ui.host.ChecklistScreen
import com.yourname.gramavasathi.viewmodel.HostViewModel
import org.junit.Rule
import org.junit.Test

class ChecklistScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun checklistFlow_updatesScoreAndNavigates() {
        val viewModel = HostViewModel()
        var finishCalled = false

        composeTestRule.setContent {
            ChecklistScreen(
                viewModel = viewModel,
                onFinish = { finishCalled = true }
            )
        }

        // 1. Verify initial score is 0
        composeTestRule.onNodeWithText("0").assertIsDisplayed()

        // 2. Tap "Done" on first item (Safe Drinking Water - weight 20)
        // This should update the score display
        composeTestRule.onNodeWithText("Done").performClick()

        // 3. Verify score updates to 20
        composeTestRule.onNodeWithText("20").assertIsDisplayed()

        // 4. Tap "Next" to advance to step 2
        composeTestRule.onNodeWithText("Next").performClick()

        // 5. Verify we are on step 2
        composeTestRule.onNodeWithText("Step 2 of 7").assertIsDisplayed()

        // 6. Navigate to the end (currently on step 2, need 5 more clicks)
        repeat(5) {
            composeTestRule.onNodeWithText("Next").performClick()
        }

        // 7. Verify "Finish & Score" appears on last step (Step 7)
        composeTestRule.onNodeWithText("Finish & Score").assertIsDisplayed()

        // 8. Tap "Finish & Score" and verify callback
        composeTestRule.onNodeWithText("Finish & Score").performClick()
        assert(finishCalled)
    }
}
