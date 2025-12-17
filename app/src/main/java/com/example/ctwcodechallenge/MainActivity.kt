package com.example.ctwcodechallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.ctwcodechallenge.ui.theme.CTWCodeChallengeTheme
import com.example.ctwcodechallenge.ui.headlines.HeadlinesScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import com.example.ctwcodechallenge.data.CURRENT_SOURCE
import androidx.compose.runtime.*
import com.example.ctwcodechallenge.data.model.Article
import com.example.ctwcodechallenge.ui.details.ArticleDetailScreen
import androidx.compose.material3.TextButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CTWCodeChallengeTheme {
                var selectedArticle by remember { mutableStateOf<Article?>(null) }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            navigationIcon = {
                                if (selectedArticle != null) {
                                    IconButton(onClick = { selectedArticle = null }) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBack,
                                            contentDescription = "Voltar"
                                        )
                                    }
                                }
                            },
                            title = {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = selectedArticle?.title ?: CURRENT_SOURCE.name,
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.SemiBold,
                                        maxLines = 1
                                    )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    val contentModifier = Modifier.padding(innerPadding)

                    if (selectedArticle == null) {
                        HeadlinesScreen(
                            modifier = contentModifier,
                            onArticleClick = { selectedArticle = it }
                        )
                    } else {
                        ArticleDetailScreen(
                            article = selectedArticle!!,
                            modifier = contentModifier
                        )
                    }
                }
            }
        }
    }
}

