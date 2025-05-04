package com.tranxortrider.deliveryrider.models

/**
 * Represents a search result item in a standardized format
 */
data class SearchResult(
    val id: String,
    val title: String,
    val subtitle: String,
    val type: SearchResultType,
    val iconResId: Int? = null
)

/**
 * Defines the type of search result
 */
enum class SearchResultType {
    ALL,
    ORDER,
    RESTAURANT,
    CUSTOMER,
    ADDRESS
} 