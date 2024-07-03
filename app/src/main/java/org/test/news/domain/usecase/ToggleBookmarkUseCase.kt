package org.test.news.domain.usecase

import org.test.news.domain.model.NewsItem
import org.test.news.domain.repository.INewsRepository
import javax.inject.Inject

class ToggleBookmarkUseCase @Inject constructor(private val repository: INewsRepository) : BaseUseCase<NewsItem, NewsItem>(){
    override suspend fun execute(params : NewsItem): NewsItem {
        if(params.isBookmarked){
            repository.onUnBookmark(params.id)
        }else{
            repository.onBookmark(params)
        }
        params.isBookmarked = !params.isBookmarked
        return params
    }
}