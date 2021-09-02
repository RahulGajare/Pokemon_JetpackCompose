package com.rg.pokemon.`interface`

import com.rg.pokemon.data.remote.response.PokemonDetail
import com.rg.pokemon.data.remote.response.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokiApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonList

    @GET("pokemon/{pokemonName}")
    suspend fun getPokemonDetail(@Path("pokemonName") pokemonName : String) : PokemonDetail

}