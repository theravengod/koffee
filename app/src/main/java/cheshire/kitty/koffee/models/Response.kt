package cheshire.kitty.koffee.models

import kotlinx.serialization.Serializable

@Serializable
data class Response<T>(
    val totalResults: Int,
    val currentPage: Int,
    val totalPages: Int,
    val items: List<T>
)
