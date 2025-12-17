package com.example.ctwcodechallenge.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ctwcodechallenge.data.model.Article
import com.example.ctwcodechallenge.ui.util.cleanNewsApiContent
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.TextButton

@Composable
fun ArticleDetailScreen(
    article: Article,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        if (!article.urlToImage.isNullOrBlank()) {
            AsyncImage(
                model = article.urlToImage,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )
        }

        Text(
            text = article.title ?: "(sem título)",
            style = MaterialTheme.typography.titleLarge
        )

        if (!article.description.isNullOrBlank()) {
            Text(
                text = article.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (!article.content.isNullOrBlank()) {
            Text(
                text = cleanNewsApiContent(article.content),
                style = MaterialTheme.typography.bodySmall
            )
        }

        if (!article.url.isNullOrBlank()) {
            TextButton(
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(article.url)
                    )
                    context.startActivity(intent)
                }
            ) {
                Text(text = "Open full article")
            }
        }
    }
}
