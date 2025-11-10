package com.example.moviesearch

import com.example.moviesearch.api.MovieSearchResponse
import com.example.moviesearch.api.OmdbApiService
import com.example.moviesearch.api.OmdbResponse
import com.example.moviesearch.api.RetrofitInstance

class MovieRepository (private val api: OmdbApiService = RetrofitInstance.api) {
    suspend fun getMovieDetails(imdbId:String): Result<OmdbResponse> {
        return try {
            val response = api.getMovieDetails(imdbId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    //Search by title
    suspend fun searchMovies(title: String): Result<MovieSearchResponse> {
        return runCatching {
            api.searchMovies(title)
        }
    }

}