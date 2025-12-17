package com.example.ctwcodechallenge.ui.headlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ctwcodechallenge.BuildConfig
import com.example.ctwcodechallenge.data.CURRENT_SOURCE
import com.example.ctwcodechallenge.data.remote.NetworkModule
import com.example.ctwcodechallenge.data.repository.NewsRepository
import com.example.ctwcodechallenge.data.repository.NewsRepositoryContract
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HeadlinesViewModel(
    private val repository: NewsRepositoryContract =
        NewsRepository(NetworkModule.api)
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<HeadlinesUiState>(HeadlinesUiState.Loading)
    val uiState: StateFlow<HeadlinesUiState> = _uiState.asStateFlow()

    init {
        loadHeadlines()
    }

    private fun loadHeadlines() {
        viewModelScope.launch {
            try {
                val articles = repository.getHeadlines(
                    CURRENT_SOURCE.id,
                    BuildConfig.NEWS_API_KEY
                )
                _uiState.value = HeadlinesUiState.Success(articles)
            } catch (t: Throwable) {
                _uiState.value = HeadlinesUiState.Error(
                    t.message ?: "Erro a carregar notícias"
                )
            }
        }
    }
}
