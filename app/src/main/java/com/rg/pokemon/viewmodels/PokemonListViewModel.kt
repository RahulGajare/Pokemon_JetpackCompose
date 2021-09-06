package com.rg.pokemon.viewmodels

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.rg.pokemon.PokemonRepositoryInterface
import com.rg.pokemon.constants.Constants.PAGE_SIZE
import com.rg.pokemon.models.PokemonEntry
import com.rg.pokemon.resource.Resoure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private var testString : String,
    private var pokemonRepo : PokemonRepositoryInterface

) : ViewModel() {

    private var currentPage = 0;
    val pokemonListState = mutableStateOf<List<PokemonEntry>>(emptyList())
    var pokemonCachedList = listOf<PokemonEntry>()
    var isInSearchMode = false
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)
    private var isSearchStarting = true

    val dominantColorState: MutableState<Color> = mutableStateOf(Color.White)
    init {
        getPokemonList()

    }



     fun getPokemonList() {
        viewModelScope.launch {
            isLoading.value = true
            var result = pokemonRepo.getPokemonList(PAGE_SIZE, currentPage * PAGE_SIZE)
            when (result) {
                is Resoure.Success -> {
                    endReached.value = currentPage * PAGE_SIZE >= result.data!!.count
                    val pokemonList = result.data!!.results
                    val pokemonEntryList = pokemonList.mapIndexed { index, item ->
                        val urlNumber = if (item.url.endsWith("/")) {
                            item.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            item.url.takeLastWhile { it.isDigit() }
                        }
                        val imageUrlAPi =
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${urlNumber}.png"
                        PokemonEntry(item.name, imageUrlAPi, urlNumber.toInt())
                    }
                    currentPage++

                    isLoading.value = false
                    loadError.value = ""
                    pokemonListState.value += pokemonEntryList


                }
                is Resoure.Error -> {
                        loadError.value = result.message.toString()
                }
                else -> {

                }
            }
        }

    }


    fun calcDominantColor(bitmap: Bitmap, onFinish: (Color) -> Unit) {

        Palette.from(bitmap).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }

    fun searchPokemon(query : String)
    {
        val listToSearch = if(isSearchStarting) {
            pokemonListState.value
        } else {
            pokemonCachedList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()) {
                pokemonListState.value = pokemonCachedList
                isInSearchMode = false
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.pokemonName.contains(query.trim(), ignoreCase = true) ||
                        it.number.toString() == query.trim()
            }
            if(isSearchStarting) {
                pokemonCachedList = pokemonListState.value
                isSearchStarting = false
            }
            pokemonListState.value = results
            isInSearchMode = true
        }

    }
}