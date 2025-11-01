package com.dharma.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dharma.pokemon.ui.theme.PokemonTheme
import com.dharma.pokemon_detail.ui.PokemonDetailScreen
import com.dharma.pokemon_list.ui.PokemonListScreen
import dagger.hilt.android.AndroidEntryPoint

/**
 * The main activity of the application.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonTheme {
                AppController()
            }
        }
    }
}

/***
 * The main composable function for the Pokemon application.
 */
@Composable
fun AppController() {
    val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "pokemon_list",
            modifier = Modifier.fillMaxSize()
        ) {
            composable("pokemon_list") {
                PokemonListScreen(
                    onPokemonClick = { pokemonId ->
                        navController.navigate("pokemon_detail/$pokemonId")
                    }
                )
            }

            composable(
                route = "pokemon_detail/{pokemonId}",
                arguments = listOf(navArgument("pokemonId") { type = NavType.IntType })
            ) {
                PokemonDetailScreen(navController)
            }
        }
}