package com.rg.pokemon.ui

import androidx.compose.animation.fadeIn
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.rg.pokemon.data.remote.response.PokemonDetail
import com.rg.pokemon.data.remote.response.Type
import com.rg.pokemon.resource.Resoure
import com.rg.pokemon.ui.utils.CircularLoader
import com.rg.pokemon.viewmodels.PokemonDetailViewModel
import java.util.*

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
            .background(Color.White),
        contentAlignment = Alignment.TopCenter,
    ) {
        TopSection(navController = navController)
        PokemonStateConditionWrapper(
            pokemonResource = pokemon,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
                .padding(
                    top = topPadding + pokemonImageSize / 2f,
                    start = 16.dp, end = 16.dp, bottom = 16.dp
                )
                .shadow(10.dp, RoundedCornerShape(10.dp)),
            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .padding(
                    top = topPadding + pokemonImageSize / 2f,
                    start = 16.dp, end = 16.dp, bottom = 16.dp
                )

        )
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
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f)
            .background(Brush.verticalGradient(listOf(Color.Black, Color.White))),
        contentAlignment = Alignment.TopStart
    )
    {
        Icon(imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .clickable { navController.popBackStack() }
                .size(35.dp)
                .offset(16.dp, 16.dp),
            tint = Color.White


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
            PokemonDetailSection(pokemonResource.data!!)

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

@Composable
fun PokemonDetailSection(pokemon: PokemonDetail) {

    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    )
    {
        Text(
            text = "#${pokemon.id}  ${
                pokemon.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }",
            textAlign = TextAlign.Center,

            color = MaterialTheme.colors.onSurface,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold


        )

        PokemonTypeSection(pokemon.types)

    }
}

@Composable
fun PokemonTypeSection(types: List<Type>) {

    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
        for (type in types) {
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .clip(CircleShape)
                    .background(Color.Yellow),
                contentAlignment = Alignment.Center
            )
            {
                Text(text = type.type.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                })
            }
        }
    }

}

