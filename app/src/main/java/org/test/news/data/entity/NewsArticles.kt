package org.test.news.data.entity

import com.google.gson.annotations.SerializedName

data class NewsArticles(
    @SerializedName("sources")
    val articleList : List<NewsItemEntity>? = null) : BaseResponse()