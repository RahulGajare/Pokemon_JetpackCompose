package com.example.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.pokemon.ui.PokemonListScreen
import com.example.pokemon.ui.Pokemon_DetailScreen
import com.example.pokemon.ui.theme.PokemonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController();
    NavHost(navController = navController, startDestination = "Pokemon_List_Screen")
    {
        composable("PokemonListScreen")
        {
            PokemonListScreen()
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

            Pokemon_DetailScreen()
        }

    }

}