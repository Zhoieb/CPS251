package com.example.moviesearch.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import androidx.navigation.NavController
import com.example.moviesearch.data.MovieViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    imdbID: String,
    navController: NavController,
    movieViewModel: MovieViewModel
) {
    var movieDetails by remember { mutableStateOf<MovieDetails?>(null) }
    val isLoading by movieViewModel.isLoading.collectAsState()
    val error by movieViewModel.error.collectAsState()

    LaunchedEffect(imdbID) {
        if(imdbID.isNotBlank()){
            movieDetails = movieViewModel.getMovieDetailsById(imdbID)
        } else {
            movieViewModel.error
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(movieDetails?.title ?: "") },
            navigationIcon = {
                Text(
                    text = "←", style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .clickable { navController.popBackStack()}
                        .padding(horizontal = 16.dp),
                    color = Color(0xFF1A237E)
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF90CAF9),
                titleContentColor = Color(0xFF0D47A1),
            )
        )
        when {
            isLoading -> CircularProgressIndicator()
            error != null -> Text("Error: $error")
            movieDetails != null -> MovieDetailsCard(movie = movieDetails!!)
        }
    }
}

@Composable
fun MovieDetailsCard(movie: MovieDetails) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = movie.posterUrl.ifBlank { null },
                    contentDescription = "Movie Poster",
                    modifier = Modifier
                        .width(150.dp)
                        .height(220.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = movie.title.ifBlank { "No title available" },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.Center

            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Year: ${movie.year.ifBlank { "N/A" }}")
            Text(text = "Rated: ${movie.rated.ifBlank { "N/A" }}")
            Text(text = "Director: ${movie.director.ifBlank { "N/A" }}")
            Text(text = "Actors: ${movie.actors.ifBlank { "N/A" }}")
            Text(text = "Rotten Tomatoes ${movie.rottenTomatoesRating ?: "N/A"}")
            Text(text = "IMDb Rating: ${movie.imdbRating.ifBlank { "N/A" }}")
            Text(text = "Box Office: ${movie.boxOffice.ifBlank { "N/A" }}")

            Spacer(modifier = Modifier.height(12.dp))

            //Plot
            Text(
                text = "Plot:",
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = movie.plot.ifBlank { "No plot available" },
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

data class MovieDetails(
    val title: String,
    val year: String,
    val rated: String,
    val director: String,
    val actors: String,
    val plot: String,
    val posterUrl: String,
    val imdbRating: String,
    val rottenTomatoesRating: String?,
    val boxOffice: String
)
