package org.test.news.presentation

import org.test.news.domain.model.NewsItem

sealed class NewsUiState {
    object LoadingStart : NewsUiState()
    object LoadingStop : NewsUiState()
    data class Success(val newsList: List<NewsItem>) : NewsUiState()
    data class Category(val categoryList: List<String>) : NewsUiState()
    data class ToggleBookmarkSuccess(val position : Int, val item: NewsItem) : NewsUiState()
    data class Error(val exceptionMessage: String?) : NewsUiState()
}
