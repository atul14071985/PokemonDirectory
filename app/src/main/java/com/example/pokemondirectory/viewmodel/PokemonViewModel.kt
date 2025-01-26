package com.example.pokemondirectory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.pokemondirectory.model.repository.PokemonRepository
import com.example.pokemondirectory.model.repository.data.PokemonDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    private val _pokemonDetails = MutableStateFlow<PokemonDetails?>(null)
    val pokemonDetails: StateFlow<PokemonDetails?> = _pokemonDetails.asStateFlow()

    fun fetchPokemonDetails(detailsUrl: String) {
        viewModelScope.launch {
            try {
                val details = pokemonRepository.getPokemonDetails(detailsUrl)
                _pokemonDetails.value = details
            } catch (e: Exception) {
            }
        }
    }

    val pokemonItems = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { pokemonRepository.getPokemonItems() }
    ).flow.cachedIn(viewModelScope)
}