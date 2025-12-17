package com.example.ctwcodechallenge.ui.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for [formatDate] utility function.
 * 
 * The function converts ISO 8601 date strings (e.g., "2024-01-15T10:30:00Z")
 * to a human-readable format (e.g., "15 Jan 2024").
 */
class DateFormatterTest {

    @Test
    fun `formats valid ISO date correctly`() {
        // Given: A valid ISO 8601 date string
        val isoDate = "2024-01-15T10:30:00Z"
        
        // When: Formatting the date
        val result = formatDate(isoDate)

        // Then: Should return formatted date
        assertEquals("15 Jan 2024", result)
    }

    @Test
    fun `formats date with different month correctly`() {
        // Given: Date in December
        val isoDate = "2024-12-25T00:00:00Z"
        
        // When: Formatting the date
        val result = formatDate(isoDate)

        // Then: Should show December
        assertEquals("25 Dec 2024", result)
    }

    @Test
    fun `returns empty string for null input`() {
        // Given: Null input
        val isoDate: String? = null
        
        // When: Formatting the date
        val result = formatDate(isoDate)

        // Then: Should return empty string
        assertEquals("", result)
    }

    @Test
    fun `returns empty string for blank input`() {
        // Given: Blank input
        val isoDate = "   "
        
        // When: Formatting the date
        val result = formatDate(isoDate)

        // Then: Should return empty string
        assertEquals("", result)
    }

    @Test
    fun `returns empty string for empty input`() {
        // Given: Empty string
        val isoDate = ""
        
        // When: Formatting the date
        val result = formatDate(isoDate)

        // Then: Should return empty string
        assertEquals("", result)
    }

    @Test
    fun `returns empty string for invalid date format`() {
        // Given: Invalid date format
        val isoDate = "not-a-valid-date"
        
        // When: Formatting the date
        val result = formatDate(isoDate)

        // Then: Should return empty string
        assertEquals("", result)
    }

    @Test
    fun `returns empty string for partial date`() {
        // Given: Incomplete ISO date
        val isoDate = "2024-01-15"
        
        // When: Formatting the date
        val result = formatDate(isoDate)

        // Then: Should return empty string (doesn't match expected format)
        assertEquals("", result)
    }

    @Test
    fun `handles date at start of year`() {
        // Given: First day of the year
        val isoDate = "2024-01-01T00:00:00Z"
        
        // When: Formatting the date
        val result = formatDate(isoDate)

        // Then: Should format correctly
        assertEquals("01 Jan 2024", result)
    }

    @Test
    fun `handles date at end of year`() {
        // Given: Last day of the year
        val isoDate = "2024-12-31T23:59:59Z"
        
        // When: Formatting the date
        val result = formatDate(isoDate)

        // Then: Should format correctly
        assertEquals("31 Dec 2024", result)
    }

    @Test
    fun `handles leap year date`() {
        // Given: Feb 29 in a leap year
        val isoDate = "2024-02-29T12:00:00Z"
        
        // When: Formatting the date
        val result = formatDate(isoDate)

        // Then: Should format correctly
        assertEquals("29 Feb 2024", result)
    }

    @Test
    fun `formatted date contains expected parts`() {
        // Given: A valid ISO date
        val isoDate = "2024-06-15T14:30:00Z"
        
        // When: Formatting the date
        val result = formatDate(isoDate)

        // Then: Should contain day, month, and year
        assertTrue("Should contain day '15'", result.contains("15"))
        assertTrue("Should contain month 'Jun'", result.contains("Jun"))
        assertTrue("Should contain year '2024'", result.contains("2024"))
    }

    @Test
    fun `handles different years`() {
        // Given: Dates from different years
        val date2020 = "2020-05-10T10:00:00Z"
        val date2023 = "2023-05-10T10:00:00Z"
        val date2025 = "2025-05-10T10:00:00Z"
        
        // When: Formatting the dates
        val result2020 = formatDate(date2020)
        val result2023 = formatDate(date2023)
        val result2025 = formatDate(date2025)

        // Then: Years should be correctly formatted
        assertTrue(result2020.contains("2020"))
        assertTrue(result2023.contains("2023"))
        assertTrue(result2025.contains("2025"))
    }

    @Test
    fun `handles midnight time`() {
        // Given: Date at midnight
        val isoDate = "2024-03-20T00:00:00Z"
        
        // When: Formatting the date
        val result = formatDate(isoDate)

        // Then: Should format correctly (time doesn't affect output)
        assertEquals("20 Mar 2024", result)
    }

    @Test
    fun `handles end of day time`() {
        // Given: Date at end of day
        val isoDate = "2024-03-20T23:59:59Z"
        
        // When: Formatting the date
        val result = formatDate(isoDate)

        // Then: Should format correctly (time doesn't affect output)
        assertEquals("20 Mar 2024", result)
    }
}

