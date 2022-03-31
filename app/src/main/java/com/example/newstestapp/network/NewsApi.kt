package com.example.newstestapp.network

import com.example.newstestapp.models.network.NewsResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v1/news")
    suspend fun getNews(
        @Query("categories") categories: String,
        @Query("keywords") keywords: String? = null,
        @Query("languages") language: String = "en",
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 25
    ): Response<NewsResponseBody>
}