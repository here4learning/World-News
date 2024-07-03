package org.test.news.presentation.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.test.news.domain.model.NewsItem
import org.test.news.domain.usecase.ToggleBookmarkUseCase
import org.test.news.domain.usecase.FetchTopNewsUseCase
import org.test.news.presentation.NewsUiState
import javax.inject.Inject

@HiltViewModel
class NewsViewModel  @Inject constructor(
    private val fetchTopNewsUseCase: FetchTopNewsUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
) : ViewModel() {

    private val _newsUiState = MutableLiveData<NewsUiState>()
    val newsUiState: LiveData<NewsUiState> get() = _newsUiState

    private fun closingForError(message : String?){
        _newsUiState.value = NewsUiState.LoadingStop
        _newsUiState.value = NewsUiState.Error(message)
    }

    fun fetchTopNews(categoryName : String? = null) {
        _newsUiState.value = NewsUiState.LoadingStart
        viewModelScope.launch {
            runCatching {
                fetchTopNewsUseCase.invoke(categoryName)
            }
            .onSuccess { list ->
                _newsUiState.value = NewsUiState.LoadingStop
                _newsUiState.value = NewsUiState.Success(list)
                if(TextUtils.isEmpty(categoryName)) {
                    _newsUiState.value =
                        NewsUiState.Category(list.mapNotNull { it.category?.replaceFirstChar { char -> char.uppercaseChar() }  }.distinct())
                }
            }.onFailure { error ->
                closingForError(error.message)
            }
        }
    }

    fun toggleBookMarkNewsDetails(position : Int,news : NewsItem) {
        _newsUiState.value = NewsUiState.LoadingStart
        viewModelScope.launch {
            runCatching {
                toggleBookmarkUseCase.invoke(news)
            }
            .onSuccess {
                _newsUiState.value = NewsUiState.LoadingStop
                _newsUiState.value = NewsUiState.ToggleBookmarkSuccess(position,it)
            }.onFailure { error ->
                closingForError(error.message)
            }
        }
    }

}