package com.rg.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.toLowerCase
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.rg.pokemon.ui.PokemonListScreen
import com.rg.pokemon.ui.Pokemon_DetailScreen
import com.rg.pokemon.ui.theme.PokemonTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Navigation()
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun Navigation() {
    val navController = rememberNavController();
    NavHost(navController = navController, startDestination = "Pokemon_List_Screen")
    {
        composable("Pokemon_List_Screen")
        {
            PokemonListScreen(navController = navController)
        }
        composable(
            "Pokemon_DetailScreen/{dominantColor}/{pokemonName}",
            arguments = listOf(navArgument(name = "dominantColor")
            {
                type = NavType.IntType
            },
                navArgument(name = "pokemonName") {
                    type = NavType.StringType
                }
            ),


            )
        {
           val dominantColor = remember{
               var color = it.arguments?.getInt("dominantColor")
               color?.let { Color(it) } ?: Color.White
           }

            val pokemonName = remember {
                it.arguments?.getString("pokemonName")
            }

            if (pokemonName != null) {
                Pokemon_DetailScreen(dominantColor = dominantColor,
                    pokemonName = pokemonName.lowercase(Locale.getDefault()), navController = navController )
            }
        }

    }



}