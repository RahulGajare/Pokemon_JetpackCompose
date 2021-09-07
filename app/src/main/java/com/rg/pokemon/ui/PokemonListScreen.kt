package com.rg.pokemon.ui

import android.graphics.Bitmap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid

import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.bitmap.BitmapPool
import coil.compose.rememberImagePainter
import coil.transform.Transformation
import com.rg.pokemon.R
import com.rg.pokemon.models.PokemonEntry
import com.rg.pokemon.viewmodels.PokemonListViewModel
import java.util.*


@ExperimentalFoundationApi
@Composable
fun PokemonListScreen(navController: NavController) {

    val viewModel: PokemonListViewModel = hiltViewModel()
    val pokemonEntries = viewModel.pokemonListState
    val isLoading by remember {
        viewModel.isLoading
    }


    var searchText by remember {
        mutableStateOf("")
    }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_internation_pok_mon_logo),
                contentDescription = "pokemon Logo",
                modifier = Modifier.fillMaxWidth()
            )
            SearchBar(
                hint = "Search...", searchText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                searchText = it
                viewModel.searchPokemon(it)
            }




            PokemonList(pokemonEntries.value, viewModel )
            {
                navController.navigate(route = "Pokemon_DetailScreen/${viewModel.dominantColorState.value.toArgb()}/${it}")

            }

          
            
        }
    }

}

@Composable
fun SearchBar(
    hint: String,
    searchText: String,
    modifier: Modifier,
    onValueChange: (String) -> Unit
) {

    Box(modifier = modifier) {
        TextField(
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .clip(AbsoluteRoundedCornerShape(20.dp))
                .shadow(5.dp, RoundedCornerShape(20.dp))
                .background(Color.White, CircleShape),

            value = searchText,
            onValueChange = {
                onValueChange(it)
            },
            placeholder = { Text(text = hint, color = Color.Gray) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Red,
                unfocusedIndicatorColor = Color.Transparent
            )


        )
    }


}


@ExperimentalFoundationApi
@Composable
fun PokemonList(list : List<PokemonEntry> ,viewModel: PokemonListViewModel , onItemClick : (pokemonName : String) -> Unit)
{
    val loadError by remember {
        viewModel.loadError
    }

    if(loadError.isNotEmpty()){
        RetrySection(onRetryCliked = {
            viewModel.getPokemonList()
            viewModel.loadError.value = "" },
            textError = loadError )
    }
    LazyVerticalGrid(contentPadding = PaddingValues(10.dp),cells = GridCells.Fixed(2))
    {

       items(list.count())
       {item ->
           if(!viewModel.isInSearchMode){
               if(item >= list.size-1)
               {
                   viewModel.getPokemonList()
               }
           }

            PokemonCard(pokemon = list[item], viewModel, onItemClick)

       }
    }




}

@ExperimentalCoilApi
@Composable
fun PokemonCard(pokemon: PokemonEntry, viewModel: PokemonListViewModel, onItemClick: (pokemonName: String) -> Unit) {

    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        Color.White
                    )
                )
            )
            .clickable {
                onItemClick.invoke(pokemon.pokemonName.lowercase(Locale.getDefault()))

            })

    {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
           PokemonImage(imageUrl = pokemon.imageUrl , viewModel){
               dominantColor = it
           }


            Text(text = pokemon.pokemonName)
        }

    }
}

@ExperimentalCoilApi
@Composable
fun PokemonImage(imageUrl: String, viewModel: PokemonListViewModel, changeDominantColor: (color : Color)->Unit) {

    Image(
        painter = rememberImagePainter(data = imageUrl,


            builder = {
                crossfade(true)
                transformations(
                    object : Transformation{
                        override fun key(): String {
                           return imageUrl
                        }

                        override suspend fun transform(
                            pool: BitmapPool,
                            input: Bitmap,
                            size: coil.size.Size
                        ): Bitmap {
                            viewModel.calcDominantColor(input){
                                color ->  changeDominantColor(color)
                            }
                            return input
                        }
                    }
                )

            }),

        contentDescription = "Pokemon image",
        modifier = Modifier.size(120.dp)
    )



}

@Composable
fun RetrySection(onRetryCliked : ()-> Unit, textError : String)
{
    Column(horizontalAlignment = Alignment.CenterHorizontally,) {
        Text(text = textError)
        Button(onClick = {onRetryCliked.invoke()}) {
            Text(text = "Retry")
        }

    }

}

@Preview
@Composable
fun PokemonListScreenPreview() {

}