package com.rg.pokemon.repository

import com.rg.pokemon.`interface`.PokiApi
import com.rg.pokemon.data.remote.response.PokemonDetail
import com.rg.pokemon.data.remote.response.PokemonList
import com.rg.pokemon.resource.Resoure
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor (private val api : PokiApi) {

    suspend fun getPokemonList(limit : Int,offset : Int) : Resoure<PokemonList> {
              val response =  try {
                    api.getPokemonList(limit,offset)
                }
                catch (ex : Exception)
                {
                   return Resoure.Error(ex.message.toString())
                }

        return Resoure.Success(response)
    }

    suspend fun getPokemonDetail(pokemonName : String) : Resoure<PokemonDetail> {
        val response =  try {
            api.getPokemonDetail(pokemonName = pokemonName)
        }
        catch (ex : Exception)
        {
            return Resoure.Error(ex.message.toString())
        }

        return Resoure.Success(response)
    }
}