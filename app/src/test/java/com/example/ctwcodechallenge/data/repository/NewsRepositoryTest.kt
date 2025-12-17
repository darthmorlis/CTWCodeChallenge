package com.example.ctwcodechallenge.data.repository

import com.example.ctwcodechallenge.data.model.Article
import com.example.ctwcodechallenge.data.model.NewsResponse
import com.example.ctwcodechallenge.data.remote.NewsApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for [NewsRepository].
 * 
 * Tests verify that the repository correctly fetches and processes
 * articles from the API, including sorting by publication date.
 */
class NewsRepositoryTest {

    @Test
    fun `getHeadlines returns articles sorted by publishedAt descending`() = runTest {
        // Given: Articles in wrong order (oldest first)
        val oldArticle = createArticle(title = "Old Article", publishedAt = "2024-01-01T10:00:00Z")
        val newArticle = createArticle(title = "New Article", publishedAt = "2024-01-02T10:00:00Z")

        val api = FakeNewsApiService(articles = listOf(oldArticle, newArticle))
        val repo = NewsRepository(api)

        // When: Getting headlines
        val result = repo.getHeadlines(sourceId = "bbc-news", apiKey = "fake-key")

        // Then: Articles should be sorted newest first
        assertEquals(listOf("New Article", "Old Article"), result.map { it.title })
    }

    @Test
    fun `getHeadlines returns empty list when API returns no articles`() = runTest {
        // Given: API returns empty list
        val api = FakeNewsApiService(articles = emptyList())
        val repo = NewsRepository(api)

        // When: Getting headlines
        val result = repo.getHeadlines(sourceId = "bbc-news", apiKey = "fake-key")

        // Then: Result should be empty
        assertTrue("Expected empty list", result.isEmpty())
    }

    @Test
    fun `getHeadlines passes correct parameters to API`() = runTest {
        // Given: API that captures the parameters
        val api = CapturingFakeNewsApiService()
        val repo = NewsRepository(api)

        // When: Getting headlines with specific parameters
        repo.getHeadlines(sourceId = "cnn", apiKey = "my-api-key")

        // Then: API should receive correct parameters
        assertEquals("cnn", api.capturedSources)
        assertEquals("my-api-key", api.capturedApiKey)
    }

    @Test
    fun `getHeadlines handles articles with null publishedAt`() = runTest {
        // Given: Articles with and without publishedAt
        val articleWithDate = createArticle(title = "With Date", publishedAt = "2024-01-02T10:00:00Z")
        val articleWithoutDate = createArticle(title = "Without Date", publishedAt = null)

        val api = FakeNewsApiService(articles = listOf(articleWithoutDate, articleWithDate))
        val repo = NewsRepository(api)

        // When: Getting headlines
        val result = repo.getHeadlines(sourceId = "bbc-news", apiKey = "fake-key")

        // Then: Should not crash and return both articles
        assertEquals(2, result.size)
    }

    @Test
    fun `getHeadlines handles articles with same publishedAt`() = runTest {
        // Given: Multiple articles with the same timestamp
        val article1 = createArticle(title = "Article 1", publishedAt = "2024-01-01T10:00:00Z")
        val article2 = createArticle(title = "Article 2", publishedAt = "2024-01-01T10:00:00Z")
        val article3 = createArticle(title = "Article 3", publishedAt = "2024-01-01T10:00:00Z")

        val api = FakeNewsApiService(articles = listOf(article1, article2, article3))
        val repo = NewsRepository(api)

        // When: Getting headlines
        val result = repo.getHeadlines(sourceId = "bbc-news", apiKey = "fake-key")

        // Then: All articles should be returned
        assertEquals(3, result.size)
    }

    @Test
    fun `getHeadlines correctly sorts multiple articles by date`() = runTest {
        // Given: Multiple articles in random order
        val jan1 = createArticle(title = "Jan 1", publishedAt = "2024-01-01T10:00:00Z")
        val jan15 = createArticle(title = "Jan 15", publishedAt = "2024-01-15T10:00:00Z")
        val jan10 = createArticle(title = "Jan 10", publishedAt = "2024-01-10T10:00:00Z")
        val jan20 = createArticle(title = "Jan 20", publishedAt = "2024-01-20T10:00:00Z")

        val api = FakeNewsApiService(articles = listOf(jan1, jan15, jan10, jan20))
        val repo = NewsRepository(api)

        // When: Getting headlines
        val result = repo.getHeadlines(sourceId = "bbc-news", apiKey = "fake-key")

        // Then: Articles should be in descending date order
        assertEquals(
            listOf("Jan 20", "Jan 15", "Jan 10", "Jan 1"),
            result.map { it.title }
        )
    }

    // region Helper methods

    /**
     * Creates a test article with the given parameters.
     * Null-safe and provides default values for optional fields.
     */
    private fun createArticle(
        title: String,
        publishedAt: String?,
        description: String? = null,
        content: String? = null,
        urlToImage: String? = null,
        url: String? = null
    ) = Article(
        title = title,
        description = description,
        content = content,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        url = url
    )

    // endregion

    // region Test doubles

    /**
     * Fake API service that returns a predefined list of articles.
     */
    private class FakeNewsApiService(
        private val articles: List<Article>
    ) : NewsApiService {
        override suspend fun getTopHeadlines(
            sources: String,
            apiKey: String
        ): NewsResponse {
            return NewsResponse(
                status = "ok",
                totalResults = articles.size,
                articles = articles
            )
        }
    }

    /**
     * Fake API service that captures the parameters passed to it.
     * Useful for verifying that correct parameters are being sent.
     */
    private class CapturingFakeNewsApiService : NewsApiService {
        var capturedSources: String? = null
        var capturedApiKey: String? = null

        override suspend fun getTopHeadlines(
            sources: String,
            apiKey: String
        ): NewsResponse {
            capturedSources = sources
            capturedApiKey = apiKey
            return NewsResponse(
                status = "ok",
                totalResults = 0,
                articles = emptyList()
            )
        }
    }

    // endregion
}
