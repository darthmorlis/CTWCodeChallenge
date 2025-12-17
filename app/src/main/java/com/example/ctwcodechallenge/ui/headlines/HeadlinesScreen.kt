package com.example.ctwcodechallenge.ui.headlines

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ctwcodechallenge.data.model.Article
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import com.example.ctwcodechallenge.ui.util.formatDate
import androidx.compose.foundation.clickable

@Composable
fun HeadlinesScreen(
    modifier: Modifier = Modifier,
    vm: HeadlinesViewModel = viewModel(),
    onArticleClick: (Article) -> Unit
) {
    val state by vm.uiState.collectAsState()

    when (val s = state) {
        is HeadlinesUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is HeadlinesUiState.Error -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = s.message)
            }
        }

        is HeadlinesUiState.Success -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(s.articles) { article ->
                    HeadlineItem(article = article, onClick = { onArticleClick(article) })
                }
            }
        }
    }
}


@Composable
fun HeadlineItem(article: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            if (!article.urlToImage.isNullOrBlank()) {
                AsyncImage(
                    model = article.urlToImage,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = article.title ?: "(sem título)",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = formatDate(article.publishedAt),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
