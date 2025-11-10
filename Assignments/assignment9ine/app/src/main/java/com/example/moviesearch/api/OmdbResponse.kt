package com.example.moviesearch.api

import com.example.moviesearch.ui.screens.MovieDetails
import com.google.gson.annotations.SerializedName


data class OmdbResponse(
    @SerializedName("Response") val response: String,
    @SerializedName("Poster") val moviePoster: String?,
    @SerializedName("Title") val movieTitle: String?,
    @SerializedName("Year") val year: String?,
    @SerializedName("Rated") val rated: String,
    @SerializedName("Director") val director: String?,
    @SerializedName("Actors") val actors: String?,
    @SerializedName("Ratings") val ratings: List<Rating>?,
    @SerializedName("imdbRating") val imdbRating: String?,
    @SerializedName("BoxOffice") val boxOffice: String?,
    @SerializedName("Plot") val plot: String?
)

data class Rating(
    @SerializedName("Source") val source: String,
    @SerializedName("Value") val value: String
)

data class MovieSearchItem(
    @SerializedName("Title") val movieTitle: String?,
    @SerializedName("Year") val year: String?,
    @SerializedName("Poster") val moviePoster: String?,
    @SerializedName("imdbID") val imdbID: String
)

data class MovieSearchResponse(
    @SerializedName("Search") val search: List<MovieSearchItem>?,
    @SerializedName("totalResults") val totalResults: String?,
    @SerializedName("Response") val response: String,
    @SerializedName("Error") val error: String?
)

fun OmdbResponse.toMovieDetails(): MovieDetails{
    val rtRating = ratings?.firstOrNull { it.source == "Rotten Tomatoes" }?.value
    return MovieDetails(
        title = movieTitle.orEmpty(),
        year = year.orEmpty(),
        rated = rated,
        director = director.orEmpty(),
        actors = actors.orEmpty(),
        plot = plot.orEmpty(),
        posterUrl = moviePoster.orEmpty(),
        imdbRating = imdbRating.orEmpty(),
        rottenTomatoesRating = rtRating,
        boxOffice = boxOffice.orEmpty()

    )
}

