package org.test.news.domain.usecase

import org.test.news.domain.NewsNotFoundException
import org.test.news.domain.model.NewsItem
import org.test.news.domain.repository.INewsRepository
import javax.inject.Inject

class FetchTopNewsUseCase @Inject constructor(private val repository: INewsRepository) : BaseUseCase<String?, List<NewsItem>>(){
    override suspend fun execute(params: String?): List<NewsItem> {
        val topHeadlines: List<NewsItem> = repository.onGetTopHeadlines() ?: throw NewsNotFoundException("Top News not found")

        val bookMarks = repository.onGetBookmarkList().map { it.id }.toSet()

        val headlines = if (params.isNullOrEmpty()) {
            topHeadlines
        } else {
            topHeadlines.filter { it.category?.lowercase() == params.lowercase() }.ifEmpty { topHeadlines }
        }

        return headlines.map { newsItem ->
            newsItem.apply {
                isBookmarked = id in bookMarks
            }
        }
    }
}