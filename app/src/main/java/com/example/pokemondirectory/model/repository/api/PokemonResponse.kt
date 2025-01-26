package com.example.pokemondirectory.model.repository.api

import com.example.pokemondirectory.model.repository.data.PokemonItem

data class PokemonResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonItem>
)
