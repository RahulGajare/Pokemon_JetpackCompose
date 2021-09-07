package com.rg.pokemon.viewmodels

import androidx.lifecycle.ViewModel
import com.rg.pokemon.PokemonRepositoryInterface
import com.rg.pokemon.data.remote.response.PokemonDetail
import com.rg.pokemon.resource.Resoure
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private var  pokeRepo: PokemonRepositoryInterface ): ViewModel() {

        suspend fun getPokemonDetail(pokemonName : String): Resoure<PokemonDetail> {
            return pokeRepo.getPokemonDetail(pokemonName)
        }
}