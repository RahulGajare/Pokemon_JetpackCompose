package com.rg.pokemon.ui

import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.rg.pokemon.data.remote.response.PokemonDetail
import com.rg.pokemon.resource.Resoure
import com.rg.pokemon.ui.utils.CircularLoader
import com.rg.pokemon.viewmodels.PokemonDetailViewModel

@Composable
fun Pokemon_DetailScreen(
    dominantColor: Color,
    pokemonName: String,
    navController: NavController,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 200.dp,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {

    val pokemon = produceState<Resoure<PokemonDetail>>(initialValue = Resoure.Loading())
    {
       value = viewModel.getPokemonDetail(pokemonName = pokemonName)
    }.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
        contentAlignment = Alignment.Center,
    ) {
//        TopSection(navController = navController)
//        PokemonStateConditionWrapper(
//            pokemonResource = pokemon,
//            modifier = Modifier
//                .fillMaxSize()
//                .clip(RoundedCornerShape(10.dp))
//                .padding(
//                    top = topPadding + pokemonImageSize / 2f,
//                    start = 16.dp, end = 16.dp, bottom = 16.dp
//                )
//                .shadow(10.dp, RoundedCornerShape(10.dp)),
//            loadingModifier = Modifier
//                .size(100.dp)
//                .align(Alignment.Center)
//                .padding(
//                    top = topPadding + pokemonImageSize / 2f,
//                    start = 16.dp, end = 16.dp, bottom = 16.dp
//                )
//
//        )
        Box(contentAlignment = Alignment.TopCenter)
        {
            if (pokemon is Resoure.Success) {
                pokemon.data?.sprites?.let {
                    Image(
                        painter = rememberImagePainter(data = it.front_default),
                        contentDescription = pokemon.data.name,
                        Modifier.size(pokemonImageSize)
                    )
                }
            }
        }

    }
}

@Composable
fun TopSection(navController: NavController) {
    Box(
        modifier = Modifier.background(Brush.verticalGradient(listOf(Color.Black, Color.White))),
        contentAlignment = Alignment.TopStart
    )
    {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back",
            modifier = Modifier.clickable { navController.popBackStack()
            }
        )
    }

}

@Composable
fun PokemonStateConditionWrapper(
    pokemonResource: Resoure<PokemonDetail>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {

    when (pokemonResource) {
        is Resoure.Success -> {

        }
        is Resoure.Error -> {

        }
        is Resoure.Loading -> {
            CircularLoader(
                color = MaterialTheme.colors.primary,
                modifier = Modifier.fillMaxSize()
            )

        }
    }

}

