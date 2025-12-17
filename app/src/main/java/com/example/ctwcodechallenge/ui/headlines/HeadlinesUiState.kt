package com.example.ctwcodechallenge.ui.headlines

import com.example.ctwcodechallenge.data.model.Article

sealed interface HeadlinesUiState {
    data object Loading : HeadlinesUiState
    data class Success(val articles: List<Article>) : HeadlinesUiState
    data class Error(val message: String) : HeadlinesUiState
}
