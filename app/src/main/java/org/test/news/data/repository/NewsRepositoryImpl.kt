package org.test.news.data.repository

import org.test.news.data.api.Api
import org.test.news.data.mapper.NewsMapper
import org.test.news.data.storage.dao.NewsDao
import org.test.news.domain.NewsNotFoundException
import org.test.news.domain.model.NewsItem
import org.test.news.domain.repository.INewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val api : Api,private val newsDao: NewsDao) : INewsRepository{
    override suspend fun onGetTopHeadlines(): List<NewsItem>? {
        val news = api.fetchTopNewsList()
        return news.articleList?.map { item ->
                NewsMapper.toDomain(item)
        }
    }

    override suspend fun onBookmark(item: NewsItem) {
        newsDao.insert(NewsMapper.toData(item))
    }

    override suspend fun onUnBookmark(id : String) {
        newsDao.deleteNewsById(id)
    }

    override suspend fun onGetBookmarkList(): List<NewsItem> {
        val news = newsDao.getBookmarkNews()
        return news.map { item ->
            NewsMapper.toDomain(item)
        }
    }
}