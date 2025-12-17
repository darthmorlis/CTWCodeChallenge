package com.example.ctwcodechallenge.data.repository

import com.example.ctwcodechallenge.data.model.Article
import com.example.ctwcodechallenge.data.remote.NewsApiService

class NewsRepository(
    private val api: NewsApiService
) : NewsRepositoryContract {

    override suspend fun getHeadlines(
        sourceId: String,
        apiKey: String
    ): List<Article> {
        val response = api.getTopHeadlines(
            sources = sourceId,
            apiKey = apiKey
        )

        return response.articles.sortedByDescending { it.publishedAt }
    }
}
