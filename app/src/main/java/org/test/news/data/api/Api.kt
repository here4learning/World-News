package org.test.news.data.api

import org.test.news.BuildConfig
import org.test.news.data.entity.NewsArticles
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("top-headlines/sources")
    suspend fun fetchTopNewsList(@Query("apiKey") apiKey: String = BuildConfig.API_KEY): NewsArticles
}