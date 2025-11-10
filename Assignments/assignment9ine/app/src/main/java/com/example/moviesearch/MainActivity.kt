package com.example.moviesearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviesearch.ui.screens.MovieSearchScreen
import com.example.moviesearch.ui.screens.MovieDetailsScreen
import com.example.moviesearch.data.MovieViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = MovieRepository()
        val factory = MovieViewModel.provideFactory(repository)

        setContent {
                val navController = rememberNavController()
                val movieViewModel: MovieViewModel = viewModel(factory = factory)

                NavHost(navController = navController, startDestination = "movieList") {
                    composable("movieList") {
                        MovieSearchScreen(
                            navController = navController,
                            movieViewModel = movieViewModel
                        )
                    }
                    composable("movieDetails/{imdbID}") { backStackEntry ->
                        val imdbID = backStackEntry.arguments?.getString("imdbID") ?: ""
                        MovieDetailsScreen(
                            imdbID = imdbID,
                            navController = navController,
                            movieViewModel = movieViewModel
                        )
                    }
                }
            }

    }
}
