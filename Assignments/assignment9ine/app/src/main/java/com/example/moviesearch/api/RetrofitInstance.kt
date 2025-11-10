package com.example.moviesearch.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASEURL = "https://www.omdbapi.com/"

    private val client = OkHttpClient.Builder().addInterceptor {
            chain -> val original = chain.request()
        val url = original.url.newBuilder()
            .addQueryParameter("apikey","bfb06722")
            .build()
        val request = original.newBuilder().url(url).build()
        chain.proceed(request)
    }.build()

    val api: OmdbApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(OmdbApiService::class.java)
    }
}