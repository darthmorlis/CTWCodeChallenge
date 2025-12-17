package com.example.ctwcodechallenge.ui.headlines

import com.example.ctwcodechallenge.data.model.Article
import com.example.ctwcodechallenge.data.repository.NewsRepositoryContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [HeadlinesViewModel].
 * 
 * Tests cover the ViewModel's behavior when loading headlines from the repository,
 * including success and error states.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class HeadlinesViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init loads headlines and emits Success state`() = runTest {
        // Given: A repository that returns a list with one article
        val fakeRepo = FakeNewsRepository(
            result = Result.success(
                listOf(
                    Article(
                        title = "Test Article",
                        description = "Test description",
                        content = "Test content",
                        urlToImage = "https://example.com/image.jpg",
                        publishedAt = "2024-01-02T10:00:00Z",
                        url = "https://example.com/article"
                    )
                )
            )
        )

        // When: ViewModel is initialized
        val vm = HeadlinesViewModel(repository = fakeRepo)
        dispatcher.scheduler.advanceUntilIdle()

        // Then: UI state should be Success
        val state = vm.uiState.value
        assertTrue("Expected Success state but was $state", state is HeadlinesUiState.Success)
    }

    @Test
    fun `init emits Error state when repository fails`() = runTest {
        // Given: A repository that returns an error
        val fakeRepo = FakeNewsRepository(
            result = Result.failure(RuntimeException("Network error"))
        )

        // When: ViewModel is initialized
        val vm = HeadlinesViewModel(repository = fakeRepo)
        dispatcher.scheduler.advanceUntilIdle()

        // Then: UI state should be Error
        val state = vm.uiState.value
        assertTrue("Expected Error state but was $state", state is HeadlinesUiState.Error)
    }

    @Test
    fun `Success state contains correct number of articles`() = runTest {
        // Given: A repository that returns multiple articles
        val articles = listOf(
            createTestArticle("Article 1"),
            createTestArticle("Article 2"),
            createTestArticle("Article 3")
        )
        val fakeRepo = FakeNewsRepository(result = Result.success(articles))

        // When: ViewModel is initialized
        val vm = HeadlinesViewModel(repository = fakeRepo)
        dispatcher.scheduler.advanceUntilIdle()

        // Then: Success state should contain all articles
        val state = vm.uiState.value as HeadlinesUiState.Success
        assertEquals(3, state.articles.size)
    }

    @Test
    fun `Success state with empty list when no articles available`() = runTest {
        // Given: A repository that returns an empty list
        val fakeRepo = FakeNewsRepository(result = Result.success(emptyList()))

        // When: ViewModel is initialized
        val vm = HeadlinesViewModel(repository = fakeRepo)
        dispatcher.scheduler.advanceUntilIdle()

        // Then: Success state should have empty list
        val state = vm.uiState.value as HeadlinesUiState.Success
        assertTrue("Expected empty articles list", state.articles.isEmpty())
    }

    @Test
    fun `Error state contains error message`() = runTest {
        // Given: A repository that fails with a specific error message
        val errorMessage = "Failed to fetch headlines"
        val fakeRepo = FakeNewsRepository(
            result = Result.failure(RuntimeException(errorMessage))
        )

        // When: ViewModel is initialized
        val vm = HeadlinesViewModel(repository = fakeRepo)
        dispatcher.scheduler.advanceUntilIdle()

        // Then: Error state should contain the error message
        val state = vm.uiState.value as HeadlinesUiState.Error
        assertEquals(errorMessage, state.message)
    }

    @Test
    fun `initial state is Loading`() = runTest {
        // Given: A repository (doesn't matter what it returns)
        val fakeRepo = FakeNewsRepository(result = Result.success(emptyList()))

        // When: ViewModel is created but coroutine hasn't completed yet
        val vm = HeadlinesViewModel(repository = fakeRepo)

        // Then: Initial state should be Loading
        val state = vm.uiState.value
        assertTrue("Expected Loading state but was $state", state is HeadlinesUiState.Loading)
    }

    // region Helper methods

    /**
     * Creates a test article with the given title.
     * Other fields are set to default test values.
     */
    private fun createTestArticle(title: String) = Article(
        title = title,
        description = "Description for $title",
        content = "Content for $title",
        urlToImage = "https://example.com/$title.jpg",
        publishedAt = "2024-01-01T10:00:00Z",
        url = "https://example.com/$title"
    )

    // endregion

    // region Test doubles

    /**
     * Fake repository implementation for testing.
     * 
     * This fake allows us to control the repository's behavior
     * without making real network calls.
     */
    private class FakeNewsRepository(
        private val result: Result<List<Article>>
    ) : NewsRepositoryContract {

        override suspend fun getHeadlines(
            sourceId: String,
            apiKey: String
        ): List<Article> {
            return result.getOrThrow()
        }
    }

    // endregion
}
