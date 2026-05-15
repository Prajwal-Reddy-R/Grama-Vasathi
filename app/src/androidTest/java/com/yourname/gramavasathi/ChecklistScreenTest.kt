package com.yourname.gramavasathi

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.yourname.gramavasathi.ui.host.ChecklistScreen
import com.yourname.gramavasathi.viewmodel.HostViewModel
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import com.yourname.gramavasathi.data.repository.ListingRepository
import com.google.firebase.auth.FirebaseAuth

class ChecklistScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun checklistFlow_updatesScoreAndNavigates() {
        // Mock dependencies to fix compilation
        val listingRepository = mock(ListingRepository::class.java)
        val auth = mock(FirebaseAuth::class.java)
        
        val viewModel = HostViewModel(listingRepository, auth)
        var finishCalled = false

        composeTestRule.setContent {
            ChecklistScreen(
                viewModel = viewModel,
                onFinished = { finishCalled = true },
                onBack = { }
            )
        }

        // 1. Verify initial score is 0
        composeTestRule.onNodeWithText("0%").assertIsDisplayed()

        // 2. Tap "Done" on first item
        // Note: The UI uses "✓ Done" from strings.xml
        composeTestRule.onNodeWithText("✓ Done").performClick()

        // 3. Verify score updates
        // Safe Drinking Water weight is 20
        composeTestRule.onNodeWithText("20%").assertIsDisplayed()

        // 4. Tap "Next" to advance
        // Note: The UI uses "Next →" from strings.xml
        composeTestRule.onNodeWithText("Next →").performClick()

        // 5. Verify we are on step 2
        composeTestRule.onNodeWithText("Step 2 of 7").assertIsDisplayed()

        // 6. Navigate to the end
        repeat(5) {
            composeTestRule.onNodeWithText("Next →").performClick()
        }

        // 7. Verify "See My Score →" appears on last step
        composeTestRule.onNodeWithText("See My Score →").assertIsDisplayed()

        // 8. Tap "See My Score →" and verify callback
        composeTestRule.onNodeWithText("See My Score →").performClick()
        assert(finishCalled)
    }
}
