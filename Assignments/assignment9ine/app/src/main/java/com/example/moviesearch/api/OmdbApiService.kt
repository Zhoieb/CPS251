package com.example.moviesearch.api

import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApiService {
    @GET("/")
    suspend fun getMovieDetails(
        @Query("i") imdbId:String
    ): OmdbResponse

    @GET("/")
    suspend fun searchMovies(
        @Query("s") title:String
    ): MovieSearchResponse
}
