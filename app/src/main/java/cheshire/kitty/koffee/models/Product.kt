package cheshire.kitty.koffee.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val name: String? = null,
    val category: String? = null,
    val isAvailable: Boolean = false
)
