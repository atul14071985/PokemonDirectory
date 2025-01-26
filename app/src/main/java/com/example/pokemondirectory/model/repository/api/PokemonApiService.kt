package com.example.pokemondirectory.model.repository.api

import com.example.pokemondirectory.model.repository.data.PokemonDetails
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonApiService {

    @GET
    suspend fun getPokemonItemsFromUrl(@Url nextUrl: String): PokemonResponse

    @GET
    suspend fun getPokemonDetails(
        @Url detailsUrl: String
    ): PokemonDetails
}