package com.example.pokemondirectory

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.pokemondirectory.view.PokemonNavHost
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class PokemonListActivityTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Inject
    lateinit var context: Context

    @Before
    fun setup() {
        // Inject the dependencies before the test starts
        hiltRule.inject()

        // Set the content for the ComposeTestRule
        composeTestRule.setContent {
            PokemonNavHost()
        }
    }

    @Test
    fun testActivityLaunchesAndDisplays() {
        composeTestRule.onNodeWithTag("pokemonList").assertIsDisplayed() // Using a test tag to identify the LazyColumn
    }

    @Test
    fun testPokemonItemClickNavigatesToDetailScreen() {
        composeTestRule.onNodeWithText("bulbasaur").performClick()
        composeTestRule.onNodeWithText("Height").assertIsDisplayed()
    }
}