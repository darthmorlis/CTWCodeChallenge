package com.example.ctwcodechallenge.ui.util

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(isoDate: String?): String {
    if (isoDate.isNullOrBlank()) return ""

    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        parser.timeZone = TimeZone.getTimeZone("UTC")

        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        formatter.format(parser.parse(isoDate)!!)
    } catch (e: Exception) {
        ""
    }
}
