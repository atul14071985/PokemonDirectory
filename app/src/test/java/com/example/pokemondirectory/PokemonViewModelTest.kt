package com.example.pokemondirectory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pokemondirectory.model.repository.PokemonRepository
import com.example.pokemondirectory.model.repository.data.PokemonDetails
import com.example.pokemondirectory.model.repository.data.Sprites
import com.example.pokemondirectory.viewmodel.PokemonViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class PokemonViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule() // For LiveData observation

    private val testDispatcher = UnconfinedTestDispatcher() // Dispatcher for coroutines

    @Mock
    lateinit var pokemonRepository: PokemonRepository

    private lateinit var pokemonViewModel: PokemonViewModel

    @Before
    fun setUp() {
        // Mock the repository
        MockitoAnnotations.openMocks(this)

        // Set the main dispatcher to our test dispatcher
        kotlinx.coroutines.Dispatchers.setMain(testDispatcher)

        // Initialize the ViewModel
        pokemonViewModel = PokemonViewModel(pokemonRepository)
    }

    @Test
    fun `test fetchPokemonDetails should update pokemonDetails`() = runTest{
        // Given
        val detailsUrl = "https://pokeapi.co/api/v2/pokemon/1/"
        val expectedDetails = PokemonDetails(name = "bulbasaur", height = 7, sprites = Sprites("some_url"))

        // Mock repository response
        Mockito.`when`(pokemonRepository.getPokemonDetails(detailsUrl)).thenReturn(expectedDetails)

        // When
        pokemonViewModel.fetchPokemonDetails(detailsUrl)

        // Then
        val result = pokemonViewModel.pokemonDetails.first()
        assert(result == expectedDetails)
    }

    @Test
    fun `test fetchPokemonDetails handles exception gracefully`() = runTest {
        // Given
        val detailsUrl = "https://pokeapi.co/api/v2/pokemon/1/"

        // Simulate repository throwing an exception
        Mockito.`when`(pokemonRepository.getPokemonDetails(detailsUrl))
            .thenThrow(RuntimeException("Network error"))

        // When
        pokemonViewModel.fetchPokemonDetails(detailsUrl)

        // Then
        // We verify that the pokemonDetails state does not get updated
        val result = pokemonViewModel.pokemonDetails.first()
        assert(result == null)
    }
}