package com.example.ctwcodechallenge.data.repository

import com.example.ctwcodechallenge.data.model.Article

interface NewsRepositoryContract {
    suspend fun getHeadlines(
        sourceId: String,
        apiKey: String
    ): List<Article>
}
