package com.example.ctwcodechallenge.ui.util

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for [cleanNewsApiContent] utility function.
 * 
 * The NewsAPI truncates content and appends a suffix like "[+780 chars]"
 * or "… [+780 chars]". These tests verify that the function correctly
 * removes these suffixes.
 */
class TextUtilsTest {

    @Test
    fun `removes truncated chars suffix with ellipsis`() {
        // Given: Content with "… [+N chars]" suffix
        val input = "Some news content… [+780 chars]"
        
        // When: Cleaning the content
        val result = cleanNewsApiContent(input)

        // Then: Suffix should be removed
        assertEquals("Some news content", result)
    }

    @Test
    fun `removes truncated chars suffix without ellipsis`() {
        // Given: Content with "[+N chars]" suffix (no ellipsis)
        val input = "Some news content [+500 chars]"
        
        // When: Cleaning the content
        val result = cleanNewsApiContent(input)

        // Then: Suffix should be removed
        assertEquals("Some news content", result)
    }

    @Test
    fun `returns empty string for null input`() {
        // Given: Null input
        val input: String? = null
        
        // When: Cleaning the content
        val result = cleanNewsApiContent(input)

        // Then: Should return empty string
        assertEquals("", result)
    }

    @Test
    fun `returns empty string for blank input`() {
        // Given: Blank input (only whitespace)
        val input = "   "
        
        // When: Cleaning the content
        val result = cleanNewsApiContent(input)

        // Then: Should return empty string
        assertEquals("", result)
    }

    @Test
    fun `returns empty string for empty input`() {
        // Given: Empty string
        val input = ""
        
        // When: Cleaning the content
        val result = cleanNewsApiContent(input)

        // Then: Should return empty string
        assertEquals("", result)
    }

    @Test
    fun `returns original content when no suffix present`() {
        // Given: Content without any truncation suffix
        val input = "This is complete content without truncation"
        
        // When: Cleaning the content
        val result = cleanNewsApiContent(input)

        // Then: Content should remain unchanged
        assertEquals("This is complete content without truncation", result)
    }

    @Test
    fun `handles content with large char count`() {
        // Given: Content with large number in suffix
        val input = "Article text… [+12345 chars]"
        
        // When: Cleaning the content
        val result = cleanNewsApiContent(input)

        // Then: Suffix should be removed
        assertEquals("Article text", result)
    }

    @Test
    fun `handles content with small char count`() {
        // Given: Content with small number in suffix
        val input = "Short text… [+5 chars]"
        
        // When: Cleaning the content
        val result = cleanNewsApiContent(input)

        // Then: Suffix should be removed
        assertEquals("Short text", result)
    }

    @Test
    fun `preserves content with brackets not matching pattern`() {
        // Given: Content with brackets that don't match the truncation pattern
        val input = "Some text [not a truncation marker]"
        
        // When: Cleaning the content
        val result = cleanNewsApiContent(input)

        // Then: Content should remain unchanged
        assertEquals("Some text [not a truncation marker]", result)
    }

    @Test
    fun `handles extra whitespace around suffix`() {
        // Given: Content with extra whitespace before suffix
        val input = "Content text   … [+100 chars]   "
        
        // When: Cleaning the content
        val result = cleanNewsApiContent(input)

        // Then: Suffix and extra whitespace should be removed
        assertEquals("Content text", result)
    }

    @Test
    fun `handles multiline content with suffix`() {
        // Given: Multiline content with suffix at the end
        val input = "First line.\nSecond line.\nThird line… [+200 chars]"
        
        // When: Cleaning the content
        val result = cleanNewsApiContent(input)

        // Then: Only suffix should be removed, content preserved
        assertEquals("First line.\nSecond line.\nThird line", result)
    }

    @Test
    fun `does not remove suffix from middle of content`() {
        // Given: Content with pattern in the middle (not at end)
        val input = "Text [+100 chars] more text after"
        
        // When: Cleaning the content
        val result = cleanNewsApiContent(input)

        // Then: Pattern in middle should NOT be removed (only at end)
        assertEquals("Text [+100 chars] more text after", result)
    }
}
