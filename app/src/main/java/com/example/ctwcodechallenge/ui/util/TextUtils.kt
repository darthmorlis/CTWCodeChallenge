package com.example.ctwcodechallenge.ui.util

fun cleanNewsApiContent(content: String?): String {
    if (content.isNullOrBlank()) return ""

    // Remove : "… [+780 chars]" or "[+780 chars]"
    return content
        .replace(Regex("\\s*…\\s*\\[\\+\\d+\\s*chars\\]\\s*$"), "")
        .replace(Regex("\\s*\\[\\+\\d+\\s*chars\\]\\s*$"), "")
        .trim()
}
