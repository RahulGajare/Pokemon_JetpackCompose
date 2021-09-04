package com.rg.pokemon.repository


import com.rg.pokemon.PokemonRepositoryInterface
import com.rg.pokemon.PokeApi
import com.rg.pokemon.data.remote.response.PokemonDetail
import com.rg.pokemon.data.remote.response.PokemonList
import com.rg.pokemon.resource.Resoure
import java.lang.Exception
import javax.inject.Inject


class PokemonRepositoryImpl @Inject constructor(
    private var pokemonApi : PokeApi
) : PokemonRepositoryInterface {


     override suspend fun getPokemonList(limit : Int, offset : Int) : Resoure<PokemonList> {
              val response =  try {
                  pokemonApi.getPokemonList(limit,offset)
                }
                catch (ex : Exception)
                {
                   return Resoure.Error(ex.message.toString())
                }

        return Resoure.Success(response)
    }

   override suspend fun getPokemonDetail(pokemonName : String): Resoure<PokemonDetail> {
        val response =  try {
            pokemonApi.getPokemonDetail(pokemonName = pokemonName)
        }
        catch (ex : Exception)
        {
            return Resoure.Error(ex.message.toString())
        }

       return Resoure.Success(response)
   }
}