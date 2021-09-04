package com.rg.pokemon

import com.rg.pokemon.data.remote.response.PokemonDetail
import com.rg.pokemon.data.remote.response.PokemonList
import com.rg.pokemon.resource.Resoure

interface PokemonRepositoryInterface {
    suspend fun getPokemonList(limit : Int,offset : Int) : Resoure<PokemonList>
    suspend fun getPokemonDetail(pokemonName : String) : Resoure<PokemonDetail>
}