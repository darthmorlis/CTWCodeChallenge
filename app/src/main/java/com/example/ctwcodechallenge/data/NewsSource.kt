package com.example.ctwcodechallenge.data

data class NewsSource(
    val id: String,
    val name: String
)

val CURRENT_SOURCE = NewsSource(
    id = "bbc-news",
    name = "BBC News"
)
