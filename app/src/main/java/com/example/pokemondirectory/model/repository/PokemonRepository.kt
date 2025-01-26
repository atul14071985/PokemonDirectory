package com.example.pokemondirectory.model.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult
import androidx.paging.PagingState
import com.example.pokemondirectory.model.repository.api.PokemonApiService
import com.example.pokemondirectory.model.repository.data.PokemonDetails
import com.example.pokemondirectory.model.repository.data.PokemonItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val apiService: PokemonApiService
) {
    // PagingSource will allow us to load pages
    fun getPokemonItems(): PagingSource<Int, PokemonItem> {
        return object : PagingSource<Int, PokemonItem>() {
            private var nextUrl: String? = "https://pokeapi.co/api/v2/pokemon/" // initial URL for the first page

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonItem> {
                return try {
                    val page = params.key ?: 1
                    // Fetch data from the API using the current page URL (`nextUrl`)
                    val response = if (nextUrl != null) {
                        apiService.getPokemonItemsFromUrl(nextUrl!!)
                    } else {
                        return LoadResult.Error(Exception("No more pages"))
                    }

                    // Update the next URL for the next request
                    nextUrl = response.next

                    // Return the loaded data
                    LoadResult.Page(
                        data = response.results,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (response.next == null) null else page + 1
                    )
                } catch (e: Exception) {
                    LoadResult.Error(e)
                }
            }

            override fun getRefreshKey(state: PagingState<Int, PokemonItem>): Int? {
                // We don't need to handle refresh logic here, but you can if needed
                return state.anchorPosition
            }
        }
    }

    suspend fun getPokemonDetails(detailsUrl: String): PokemonDetails {
        return apiService.getPokemonDetails(detailsUrl)
    }
}
