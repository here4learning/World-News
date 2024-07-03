package org.test.news.domain.repository

import org.test.news.domain.model.NewsItem

interface INewsRepository {
    suspend fun onGetTopHeadlines() : List<NewsItem>?
    suspend fun onBookmark(item : NewsItem)
    suspend fun onUnBookmark(id : String)
    suspend fun onGetBookmarkList() : List<NewsItem>
}