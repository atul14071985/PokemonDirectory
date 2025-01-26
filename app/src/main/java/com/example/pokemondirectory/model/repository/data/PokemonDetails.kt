package com.example.pokemondirectory.model.repository.data

import com.google.gson.annotations.SerializedName

data class PokemonDetails(
    val name: String,
    val height: Int,
    val sprites: Sprites
)

data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String?
)