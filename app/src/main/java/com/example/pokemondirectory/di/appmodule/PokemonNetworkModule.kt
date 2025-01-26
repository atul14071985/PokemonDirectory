package com.example.pokemondirectory.di.appmodule

import com.example.pokemondirectory.model.repository.PokemonRepository
import com.example.pokemondirectory.model.repository.api.PokemonApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonNetworkModule {
    private const val POKEMON_URL = "https://pokeapi.co/api/v2/pokemon/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(POKEMON_URL) // Replace with actual base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): PokemonApiService {
        return retrofit.create(PokemonApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonRepository(api: PokemonApiService): PokemonRepository {
        return PokemonRepository(api)
    }
}