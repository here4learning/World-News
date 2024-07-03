package org.test.news.data.mapper

import org.test.news.data.entity.NewsItemEntity
import org.test.news.domain.model.NewsItem

object NewsMapper {

    fun toDomain(newsItemEntity: NewsItemEntity): NewsItem {
        return NewsItem(
            id = newsItemEntity.id,
            name = newsItemEntity.name,
            description = newsItemEntity.description,
            url = newsItemEntity.url,
            category = newsItemEntity.category
        )
    }

    fun toData(news: NewsItem): NewsItemEntity {
        return NewsItemEntity(
            id = news.id,
            name = news.name,
            description = news.description,
            url = news.url,
            category = news.category
        )
    }
}