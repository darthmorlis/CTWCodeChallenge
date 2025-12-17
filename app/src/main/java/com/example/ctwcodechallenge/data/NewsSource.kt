package com.example.ctwcodechallenge.data

import com.example.ctwcodechallenge.BuildConfig

/**
 * Represents a news source configuration.
 *
 * The source is determined at build time via product flavors.
 * Each flavor defines its own NEWS_SOURCE_ID and NEWS_SOURCE_NAME in BuildConfig.
 */
data class NewsSource(
    val id: String,
    val name: String
)

/**
 * The current news source for this build variant.
 * 
 * This value is set at compile time based on the selected product flavor:
 * - bbcDebug / bbcRelease -> BBC News
 * - cnnDebug / cnnRelease -> CNN
 */
val CURRENT_SOURCE = NewsSource(
    id = BuildConfig.NEWS_SOURCE_ID,
    name = BuildConfig.NEWS_SOURCE_NAME
)
